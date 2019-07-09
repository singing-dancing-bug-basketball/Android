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
import kotlinx.coroutines.launch

class ExamActivity : AppCompatActivity() {
    private lateinit var exam: Exam
    private lateinit var timeLeft: TextView
    private lateinit var questionLeft: TextView
    private lateinit var questionList: ListView
    private lateinit var questionAdapter: QuestionAdapter

    private val startHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            startExam()
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
        findViewById<Button>(R.id.start_exam_button).setOnClickListener { button ->
            button.isEnabled = false
            GlobalScope.launch {
                questionAdapter = QuestionAdapter(this@ExamActivity, Question.list(exam.id))
                startHandler.sendEmptyMessage(0)
            }
        }
    }

    private fun startExam() {
        findViewById<View>(R.id.start_exam_frame).visibility = View.GONE
        val submitFrame = this.layoutInflater.inflate(
            R.layout.submit_button, questionList, false
        ) as LinearLayout
        questionList.addFooterView(submitFrame)
        questionList.adapter = questionAdapter
        questionList.visibility = View.VISIBLE
        submitFrame.findViewById<Button>(R.id.submit_button).setOnClickListener { button ->
            Toast.makeText(this@ExamActivity, "submit!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitExam() {

    }

    private fun postSubmitExam() {

    }
}
