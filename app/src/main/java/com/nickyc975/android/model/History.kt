package com.nickyc975.android.model

import kotlinx.coroutines.delay
import java.util.*

class History private constructor(
    id: Int,
    title: String,
    deadline: Date,
    time: Int,
    totalScore: Double,
    numQuestions: Int,
    val userScore: Double = 0.0
): Exam(id, title, deadline, time, totalScore, numQuestions) {
    companion object {
        @JvmStatic
        val histories = listOf(
            History(0, "exam_4", Date(), 120, 100.0, 0, 90.0),
            History(1, "exam_5", Date(), 120, 100.0, 0, 85.0),
            History(2, "exam_6", Date(), 120, 100.0, 0, 95.0)
        )

        @JvmStatic
        suspend fun list(): List<History> {
            delay(500)
            return histories
        }

        suspend fun get(id: Int): History {
            delay(500)
            return histories[id]
        }
    }
}