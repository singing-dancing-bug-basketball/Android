package com.nickyc975.android.model

import java.util.Date

class Exam(
    val id: Int,
    val title: String,
    val deadline: Date,
    val time: Int,
    val totalScore: Double,
    val questions: List<Question>? = null,
    val participated: Boolean = false,
    val userScore: Double = 0.0): Model()