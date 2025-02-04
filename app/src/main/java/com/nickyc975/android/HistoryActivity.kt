package com.nickyc975.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nickyc975.android.adapter.HistoryQuestionAdapter
import com.nickyc975.android.model.History
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity(), FailHandler {
    override val failMessageHandler = FailHandler.Companion.FailMessageHandler(this)

    private lateinit var history: History
    private lateinit var questionList: ListView
    private lateinit var questionAdapter: HistoryQuestionAdapter

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
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
            history = History.get(this@HistoryActivity, history)
            questionAdapter = HistoryQuestionAdapter(this@HistoryActivity, history.questions)
            handler.sendEmptyMessage(0)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onLoaded() {
        questionList.adapter = questionAdapter
        var correctCount = 0
        for (question in history.questions) {
            if (question.selected == question.answer) {
                correctCount++
            }
        }
        findViewById<TextView>(R.id.question_correct).text = "$correctCount/${history.questions.size}"
        findViewById<TextView>(R.id.total_score).text = "${history.userScore}/${history.totalScore}"
    }
}
