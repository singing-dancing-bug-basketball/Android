package com.nickyc975.android.model

import kotlinx.coroutines.delay
import java.io.Serializable

class Question private constructor(
    val id: Int,
    val title: String,
    val answer: String?,
    var selected: String?,
    val options: Map<String, String>
): Model(), Serializable {
    companion object {
        @JvmStatic
        val questions = listOf(
            Question(1, "question_1", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
            Question(2, "question_2", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
            Question(3, "question_3", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
            Question(4, "question_4", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
            Question(5, "question_5", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126"))
        )

        @JvmStatic
        suspend fun list(examId: Int): List<Question> {
            delay(500)
            return questions
        }
    }
}