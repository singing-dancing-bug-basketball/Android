package com.nickyc975.android.model

import java.util.Date

internal object Data {
    val exams = listOf(
        Exam(1, "exam_1", Date(), 120, 100.0),
        Exam(2, "exam_2", Date(), 120, 100.0),
        Exam(3, "exam_3", Date(), 120, 100.0)
    )

    val histories = listOf(
        Exam(4, "exam_4", Date(), 120, 100.0, null, true, 90.0),
        Exam(5, "exam_5", Date(), 120, 100.0, null, true, 85.0),
        Exam(6, "exam_6", Date(), 120, 100.0, null, true, 95.0)
    )

    val questions = listOf(
        Question(1, "question_1", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
        Question(2, "question_2", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
        Question(3, "question_3", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
        Question(4, "question_4", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126")),
        Question(5, "question_5", null, null, mapOf("A" to "123", "B" to "124", "C" to "125", "D" to "126"))
    )
}