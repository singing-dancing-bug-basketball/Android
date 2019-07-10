package com.nickyc975.android.model

import kotlinx.coroutines.delay
import java.io.Serializable
import java.util.Date

open class Exam protected constructor(
    val id: Int,
    val title: String,
    val deadline: Date,
    val time: Int,
    val totalScore: Double,
    val numQuestions: Int = 0,
    val questions: List<Question>? = null
) : Model(), Serializable {
    companion object {
        @JvmStatic
        val exams = listOf(
            Exam(1, "exam_1", Date(), 120, 100.0, 5),
            Exam(2, "exam_2", Date(), 120, 100.0, 5),
            Exam(3, "exam_3", Date(), 120, 100.0, 5)
        )

        @JvmStatic
        suspend fun list(): List<Exam> {
            delay(500)
            return exams
        }

        @JvmStatic
        suspend fun get(id: Int): Exam {
            delay(500)
            return exams[id]
        }

        @JvmStatic
        suspend fun submit(exam: Exam): Boolean {
            delay(500)
            return true
        }
    }

}