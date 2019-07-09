package com.nickyc975.android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.nickyc975.android.adapter.QuestionAdapter
import com.nickyc975.android.model.Exam
import com.nickyc975.android.model.Question

class ExamActivity : AppCompatActivity() {
    private lateinit var exam: Exam
    private lateinit var timeLeft: TextView
    private lateinit var questionLeft: TextView
    private lateinit var questionList: ListView

    private val startHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            postStartExam()
        }
    }

    private val submitHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            postSubmitExam()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        timeLeft = findViewById(R.id.time_left)
        questionLeft = findViewById(R.id.question_left)
        questionList = findViewById(R.id.question_list)
        initExam()
    }

    @SuppressLint("SetTextI18n")
    private fun initExam() {
        questionList.visibility = View.GONE
        exam = intent.getSerializableExtra("exam") as Exam
        timeLeft.text = DateUtils.formatElapsedTime(exam.time.toLong() * 60)
        questionLeft.text = "0/${exam.numQuestions}"
        val startButton: Button = findViewById(R.id.start_exam_button)
    }

    private fun startExam() {
        val submitFrame: LinearLayout = this.layoutInflater.inflate(
            R.layout.submit_button, questionList, false) as LinearLayout
        val submitButton: Button = submitFrame.findViewById(R.id.submit_button)
        // questionList.adapter = QuestionAdapter(this, Question.list(exam.id))
        questionList.addFooterView(submitFrame)
    }

    private fun postStartExam() {

    }

    private fun submitExam() {

    }

    private fun postSubmitExam() {

    }
}
