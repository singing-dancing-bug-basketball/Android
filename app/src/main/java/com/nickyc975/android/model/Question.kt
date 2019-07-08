package com.nickyc975.android.model

data class Question(
    val id: Int,
    val title: String,
    val answer: String?,
    var selected: String?,
    val options: Map<String, String>
)