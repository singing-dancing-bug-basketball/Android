package com.nickyc975.android.model

import org.json.JSONObject
import java.io.Serializable

class Question private constructor(
    val id: Int,
    val title: String,
    val score: Int,
    val answer: Int?,
    var selected: Int?,
    val options: List<String>
): Model(), Serializable {
    companion object {
        @JvmStatic
        fun parse(JSONQuestion: JSONObject): Question {
            val options = ArrayList<String>()
            val JSONOptions = JSONQuestion.getJSONArray("selections")
            for (i in 0 until JSONOptions.length()) {
                options.add(JSONOptions.getString(i))
            }

            return Question(
                JSONQuestion.getInt("question_id"),
                JSONQuestion.getString("stem"),
                JSONQuestion.getInt("score"),
                JSONQuestion.optInt("answer"),
                JSONQuestion.optInt("selection"),
                options
            )
        }
    }
}