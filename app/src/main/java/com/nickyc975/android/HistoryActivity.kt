package com.nickyc975.android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ListView
import com.nickyc975.android.adapter.HistoryQuestionAdapter
import com.nickyc975.android.model.History
import com.nickyc975.android.model.Question
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var history: History
    private lateinit var questionList: ListView
    private lateinit var questionAdapter: HistoryQuestionAdapter

    private val handler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            onLoaded()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        questionList = findViewById(R.id.question_list)
        history = intent.getSerializableExtra("history") as History
        GlobalScope.launch {
            questionAdapter = HistoryQuestionAdapter(this@HistoryActivity, Question.list(history.id))
            handler.sendEmptyMessage(0)
        }
    }

    private fun onLoaded() {
        questionList.adapter = questionAdapter
    }
}
