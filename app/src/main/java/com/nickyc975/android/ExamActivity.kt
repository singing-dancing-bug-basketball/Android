package com.nickyc975.android

import android.annotation.SuppressLint
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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ExamActivity : AppCompatActivity() {
    private lateinit var exam: Exam
    private lateinit var timeLeft: TextView
    private lateinit var questionLeft: TextView
    private lateinit var questionList: ListView
    private lateinit var timer: Job
    private lateinit var questionAdapter: QuestionAdapter

    private var started = false
    private var submitted = false
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
            postSubmitExam()
        }
    }

    @SuppressLint("SetTextI18n")
    fun onQuestionChecked() {
        if (numQuestionLeft > 0) {
            numQuestionLeft--
            questionLeft.text = "$numQuestionLeft/${exam.numQuestions}"
        }
    }

    @SuppressLint("SetTextI18n")
    fun onTimeCountDown() {
        if (numTimeLeft > 0) {
            numTimeLeft--
            timeLeft.text = DateUtils.formatElapsedTime(numTimeLeft.toLong())
        }

        if (numTimeLeft <= 0) {
            submitExam()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        if (started && !submitted) {
            submitExam()
        }
    }

    override fun onBackPressed() {
        if (started && !submitted) {
            Toast.makeText(this, R.string.not_submitted, Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
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
        submitFrame.findViewById<Button>(R.id.submit_button).setOnClickListener { button -> submitExam() }

        timer = GlobalScope.launch {
            while (isActive) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
                countDownHandler.sendEmptyMessage(0)
            }
        }

        started = true
    }

    private fun submitExam() {
        timer.cancel()
        submitted = true
        questionAdapter.disableAll()
        findViewById<Button>(R.id.submit_button).isEnabled = false
        GlobalScope.launch {
            Exam.submit(exam.id, questionAdapter.getResult())
            submitHandler.sendEmptyMessage(0)
        }
    }

    private fun postSubmitExam() {
        Toast.makeText(this, R.string.submitted, Toast.LENGTH_SHORT).show()
    }
}
