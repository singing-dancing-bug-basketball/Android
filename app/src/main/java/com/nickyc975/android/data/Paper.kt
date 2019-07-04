package com.nickyc975.android.data

data class Paper(
    val id: Int,
    val time: Int,
    val totalScore: Double,
    val questions: List<Question>
)