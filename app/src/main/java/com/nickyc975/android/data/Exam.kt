package com.nickyc975.android.data

import java.util.Date

data class Exam(
    val id: Int,
    val paper: Paper,
    val title: String,
    val deadline: Date,
    val participated: Boolean = false,
    val userScore: Double = 0.0
)