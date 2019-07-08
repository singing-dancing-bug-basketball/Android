package com.nickyc975.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.nickyc975.android.adapter.QuestionAdapter
import com.nickyc975.android.model.Data

class ExamActivity : AppCompatActivity() {
    private lateinit var questionList: ListView
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        questionList = findViewById(R.id.question_list)

        questionAdapter = QuestionAdapter(this, Data.questions)
        questionList.adapter = questionAdapter
    }
}
