package com.nickyc975.android.data

data class Question(
    val id: Int,
    val title: String,
    val answer: String,
    val options: Map<String, String>
)