package com.nickyc975.android.model

import kotlinx.coroutines.delay
import java.io.Serializable

class Question private constructor(
    val id: Int,
    val title: String,
    val answer: Int?,
    var selected: Int?,
    val options: Map<Int, String>
): Model(), Serializable {
    companion object {
        @JvmStatic
        val questions = listOf(
            Question(1, "question_1", 1, 2, mapOf(1 to "123", 2 to "124", 3 to "125", 4 to "126")),
            Question(2, "question_2", 2, 1, mapOf(1 to "123", 2 to "124", 3 to "125", 4 to "126")),
            Question(3, "question_3", 3, 3, mapOf(1 to "123", 2 to "124", 3 to "125", 4 to "126")),
            Question(4, "question_4", 4, 4, mapOf(1 to "123", 2 to "124", 3 to "125", 4 to "126")),
            Question(5, "question_5", 1, 4, mapOf(1 to "123", 2 to "124", 3 to "125", 4 to "126"))
        )

        @JvmStatic
        suspend fun list(examId: Int): List<Question> {
            delay(500)
            return questions
        }
    }
}