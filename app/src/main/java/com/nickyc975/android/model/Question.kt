package com.nickyc975.android.model

import kotlinx.coroutines.delay
import org.json.JSONObject
import java.io.Serializable

class Question private constructor(
    val id: Int,
    val title: String,
    val score: Int,
    val answer: Int?,
    var selected: Int?,
    val options: List<String>
): Model(), Serializable {
    companion object {
        @JvmStatic
        val questions = listOf(
            Question(1, "question_1", 10, 1, 2, listOf("123", "124", "125", "126")),
            Question(2, "question_2", 10, 2, 1, listOf("123", "124", "125", "126")),
            Question(3, "question_3", 10, 3, 3, listOf("123", "124", "125", "126")),
            Question(4, "question_4", 10, 4, 4, listOf("123", "124", "125", "126")),
            Question(5, "question_5", 10, 1, 4, listOf("123", "124", "125", "126"))
        )

        @JvmStatic
        suspend fun list(examId: Int): List<Question> {
            delay(500)
            return questions
        }

        @JvmStatic
        fun parse(JSONQuestion: JSONObject): Question {
            val options = ArrayList<String>()
            val JSONOptions = JSONQuestion.getJSONArray("selections")
            for (i in 0 until JSONOptions.length()) {
                options.add(JSONOptions.getString(i))
            }

            return Question(
                JSONQuestion.getInt("question_id"),
                JSONQuestion.getString("stem"),
                JSONQuestion.getInt("score"), null, null,
                options
            )
        }
    }
}