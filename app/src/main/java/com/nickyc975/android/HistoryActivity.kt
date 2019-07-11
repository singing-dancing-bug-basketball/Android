package com.nickyc975.android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.nickyc975.android.adapter.HistoryQuestionAdapter
import com.nickyc975.android.model.History
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    lateinit var failReason: FailReason
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
            history = History.get(this@HistoryActivity, history)
            if (history.questions.isNotEmpty()) {
                questionAdapter = HistoryQuestionAdapter(this@HistoryActivity, history.questions)
                handler.sendEmptyMessage(0)
            } else {
                val messageId = when (failReason) {
                    FailReason.NETWORK_ERROR -> R.string.network_error
                    FailReason.USER_NOT_LOGEDIN -> R.string.network_error
                    else -> R.string.unknown_error
                }
                Toast.makeText(this@HistoryActivity, messageId, Toast.LENGTH_SHORT).show()
            }
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
