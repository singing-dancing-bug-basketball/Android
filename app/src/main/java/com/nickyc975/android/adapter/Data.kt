package com.nickyc975.android.adapter

import com.nickyc975.android.data.Exam
import com.nickyc975.android.data.Paper
import com.nickyc975.android.data.Question
import java.util.*

internal object Data {
    val exams = listOf(
        Exam(
            1,
            Paper(
                1, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_1", Date()
        ),
        Exam(
            2,
            Paper(
                2, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_2", Date()
        ),
        Exam(
            3,
            Paper(
                3, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_3", Date()
        )
    )

    val histories = listOf(
        Exam(
            4,
            Paper(
                4, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_4", Date(), true, 90.0
        ),
        Exam(
            5,
            Paper(
                5, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_5", Date(), true, 85.0
        ),
        Exam(
            6,
            Paper(
                6, 120, 100.0, listOf(
                    Question(
                        1, "a", "b", mapOf()
                    )
                )
            ),
            "exam_6", Date(), true, 95.0
        )
    )
}