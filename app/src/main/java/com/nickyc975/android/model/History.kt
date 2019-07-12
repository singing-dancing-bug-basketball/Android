package com.nickyc975.android.model

import android.content.Context
import android.util.Log
import com.nickyc975.android.FailHandler
import com.nickyc975.android.FailHandler.Companion.FailReason
import com.nickyc975.android.R
import okhttp3.Request
import org.json.JSONObject
import java.util.*

class History private constructor(
    id: Int,
    title: String,
    deadline: Date,
    time: Int,
    totalScore: Double,
    numQuestions: Int,
    val userScore: Double = 0.0,
    questions: List<Question> = listOf()
) : Exam(id, title, deadline, time, totalScore, numQuestions, questions) {
    companion object {
        @JvmStatic
        private val LOG_TAG = "HistoryModel"

        @JvmStatic
        suspend fun list(context: Context): List<History> {
            if (!User.isLogedin(context)) {
                (context as FailHandler).onFail(FailReason.USER_NOT_LOGEDIN)
                return listOf()
            }

            val histories = ArrayList<History>()
            val user = AppDatabase.getDatabase(context)?.userDao()?.current()
            val request = Request.Builder()
                .header("Cookie", user!!.cookie)
                .url(
                    context.getString(R.string.base_url, context.getString(R.string.history_list_url, user.id))
                )
                .get()
                .build()

            try {
                val response = client.newCall(request).execute()
                val result = JSONObject(response.body()?.string())
                Log.d(LOG_TAG, result.toString())
                val JSONHistories = result.getJSONArray("tests")
                for (i in 0 until JSONHistories.length()) {
                    histories.add(parse(JSONHistories.getJSONObject(i)))
                }
            } catch (e: Exception) {
                (context as FailHandler).onFail(FailReason.NETWORK_ERROR)
            }

            return histories
        }

        @JvmStatic
        suspend fun get(context: Context, old: History): History {
            if (!User.isLogedin(context)) {
                (context as FailHandler).onFail(FailReason.USER_NOT_LOGEDIN)
                return old
            }

            val user = AppDatabase.getDatabase(context)?.userDao()?.current()
            val request = Request.Builder()
                .header("Cookie", user!!.cookie)
                .url(
                    context.getString(
                        R.string.base_url,
                        context.getString(R.string.history_url, "${user.id}/${old.id}")
                    )
                )
                .get()
                .build()

            try {
                val questions = ArrayList<Question>()
                val response = client.newCall(request).execute()
                val result = JSONObject(response.body()?.string())
                Log.d(LOG_TAG, result.toString())
                val JSONQuestions = result.getJSONArray("questions")
                for (i in 0 until JSONQuestions.length()) {
                    questions.add(Question.parse(JSONQuestions.getJSONObject(i)))
                }
                old.questions = questions
            } catch (e: Exception) {
                (context as FailHandler).onFail(FailReason.NETWORK_ERROR)
            }

            return old
        }

        @JvmStatic
        fun parse(JSONHistory: JSONObject): History {
            return History(
                JSONHistory.getInt("test_id"),
                JSONHistory.getString("title"),
                Date(JSONHistory.getLong("end_time")),
                JSONHistory.getInt("duration"),
                JSONHistory.getDouble("total_score"),
                0,
                JSONHistory.getDouble("user_score")
            )
        }
    }
}