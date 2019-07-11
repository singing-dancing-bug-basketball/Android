package com.nickyc975.android.model

import android.content.Context
import com.nickyc975.android.R
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

open class Exam protected constructor(
    val id: Int,
    val title: String,
    val deadline: Date,
    val time: Int,
    val totalScore: Double,
    val numQuestions: Int = 0,
    var questions: List<Question> = listOf()
) : Model(), Serializable {
    companion object {
        @JvmStatic
        suspend fun list(context: Context): List<Exam> {
            if (!User.isLogedin(context)) {
                return listOf()
            }

            val user = AppDatabase.getDatabase(context)?.userDao()?.current()
            val request = Request.Builder()
                .header("Cookie", user!!.cookie)
                .url(
                    context.getString(R.string.base_url, context.getString(R.string.exam_list_url, user.id))
                )
                .get()
                .build()

            try {
                val exams = ArrayList<Exam>()
                val response = client.newCall(request).execute()
                val result = JSONObject(response.body()?.string())
                val JSONExams = result.getJSONArray("tests")
                for (i in 0 until  JSONExams.length()) {
                    exams.add(parse(JSONExams.getJSONObject(i)))
                }
                return exams
            } catch (e: Exception) {
                return listOf()
            }
        }

        @JvmStatic
        suspend fun get(context: Context, old: Exam): Exam {
            if (!User.isLogedin(context)) {
                return old
            }

            val user = AppDatabase.getDatabase(context)?.userDao()?.current()
            val request = Request.Builder()
                .header("Cookie", user!!.cookie)
                .url(
                    context.getString(R.string.base_url, context.getString(R.string.exam_url, old.id.toString()))
                )
                .get()
                .build()

            println(request.url().toString())

            try {
                val questions = ArrayList<Question>()
                val response = client.newCall(request).execute()
                val result = JSONObject(response.body()?.string())
                val JSONQuestions = result.getJSONArray("questions")
                for (i in 0 until JSONQuestions.length()) {
                    questions.add(Question.parse(JSONQuestions.getJSONObject(i)))
                }
                old.questions = questions
            } catch (e: Exception) {

            }

            return old
        }

        @JvmStatic
        suspend fun submit(context: Context, examId: Int, result: Map<Int, Int>): Boolean {
            if (!User.isLogedin(context)) {
                return false
            }

            val json = JSONObject()
            val array = JSONArray()
            for ((question_id, selected) in result.entries) {
                val question = JSONObject()
                question.put("question_id", question_id)
                question.put("selection", selected)
                array.put(question)
            }
            val user = AppDatabase.getDatabase(context)?.userDao()?.current()
            json.put("student_id", user?.id)
            json.put("test_id", examId)
            json.put("selections", array)

            val body = RequestBody.create(JSON, json.toString())
            val request = Request.Builder()
                .header("Cookie", user!!.cookie)
                .url(
                    context.getString(R.string.base_url, context.getString(R.string.history_url, ""))
                )
                .post(body)
                .build()

            try {
                val response = JSONObject(client.newCall(request).execute().body()?.string())
                if (response.getInt("status") == 200) {
                    return true
                }
                return false
            } catch (e: Exception) {
                return false
            }
        }

        @JvmStatic
        fun parse(JSONExam: JSONObject): Exam {
            return Exam(
                JSONExam.getInt("test_id"),
                JSONExam.getString("title"),
                Date(JSONExam.getLong("end_time")),
                JSONExam.getInt("duration"),
                JSONExam.getDouble("total_score"),
                JSONExam.getInt("num_questions")
            )
        }
    }
}