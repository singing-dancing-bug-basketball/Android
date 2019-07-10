package com.nickyc975.android

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.format.DateUtils
import android.view.View
import android.widget.*
import com.nickyc975.android.adapter.QuestionAdapter
import com.nickyc975.android.model.Exam
import com.nickyc975.android.model.Question
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExamActivity : AppCompatActivity() {
    private lateinit var exam: Exam
    private lateinit var timeLeft: TextView
    private lateinit var questionLeft: TextView
    private lateinit var questionList: ListView
    private lateinit var timer: Job
    private lateinit var questionAdapter: QuestionAdapter

    private var numTimeLeft: Int = 0
    private var numQuestionLeft: Int = 0

    private val startHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            startExam()
        }
    }

    private val countDownHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            onTimeCountDown()
        }
    }

    private val submitHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            submitExam()
        }
    }

    @SuppressLint("SetTextI18n")
    fun onQuestionChecked() {
        numQuestionLeft--
        questionLeft.text = "$numQuestionLeft/${exam.numQuestions}"
    }

    @SuppressLint("SetTextI18n")
    fun onTimeCountDown() {
        numTimeLeft--
        timeLeft.text = DateUtils.formatElapsedTime(numTimeLeft.toLong())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        timeLeft = findViewById(R.id.time_left)
        questionLeft = findViewById(R.id.question_left)
        questionList = findViewById(R.id.question_list)
        exam = intent.getSerializableExtra("exam") as Exam
        initExam()
    }

    @SuppressLint("SetTextI18n")
    private fun initExam() {
        questionList.visibility = View.GONE
        numTimeLeft = exam.time * 60
        numQuestionLeft = exam.numQuestions
        questionLeft.text = "$numQuestionLeft/${exam.numQuestions}"
        timeLeft.text = DateUtils.formatElapsedTime(numTimeLeft.toLong())
        findViewById<Button>(R.id.start_exam_button).setOnClickListener { button ->
            button.isEnabled = false
            GlobalScope.launch {
                exam = Exam.get(exam.id)
                questionAdapter = QuestionAdapter(this@ExamActivity, Question.list(exam.id))
                startHandler.sendEmptyMessage(0)
            }
        }
    }

    private fun startExam() {
        findViewById<View>(R.id.start_exam_frame).visibility = View.GONE
        val submitFrame = this.layoutInflater.inflate(
            R.layout.submit_button, questionList, false) as LinearLayout
        questionList.addFooterView(submitFrame)
        questionList.adapter = questionAdapter
        questionList.visibility = View.VISIBLE
        submitFrame.findViewById<Button>(R.id.submit_button).setOnClickListener { button ->
            questionList.isEnabled = false
            GlobalScope.launch {
                Exam.submit(exam)
                submitHandler.sendEmptyMessage(0)
            }
        }

        timer = GlobalScope.launch {
            while (true) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
                countDownHandler.sendEmptyMessage(0)
            }
        }
    }

    private fun submitExam() {

    }
}
