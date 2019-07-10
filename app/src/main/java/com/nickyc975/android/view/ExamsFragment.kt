package com.nickyc975.android.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nickyc975.android.ExamActivity
import com.nickyc975.android.R
import com.nickyc975.android.adapter.ExamAdapter
import com.nickyc975.android.model.Exam
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExamsFragment: ToolbarFragment() {
    private var needRefresh = false
    private lateinit var handler: Handler
    private lateinit var examList: ListView
    private lateinit var examAdapter: ExamAdapter
    private lateinit var examsRefresh: SwipeRefreshLayout

    private var listener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val intent = Intent(this@ExamsFragment.context, ExamActivity::class.java)
        intent.putExtra("exam", examAdapter.getItem(position))
        startActivity(intent)
    }

    override fun requireRefresh() {
        synchronized(needRefresh) {
            needRefresh = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        handler = @SuppressLint("HandlerLeak")
        object: Handler() {
            override fun handleMessage(msg: Message) {
                examAdapter.notifyDataSetChanged()
                examsRefresh.isRefreshing = false
            }
        }
        return inflater.inflate(R.layout.layout_exams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.exam)
        examList = view.findViewById(R.id.exam_list)
        examsRefresh = view.findViewById(R.id.exams_refresh)
        examAdapter = ExamAdapter(activity as Activity, listOf())
        examList.adapter = examAdapter
        examsRefresh.setOnRefreshListener { doRefresh() }
        examList.onItemClickListener = listener
        requireRefresh()
    }

    override fun onStart() {
        super.onStart()
        synchronized(needRefresh) {
            if (needRefresh) {
                needRefresh = false
                doRefresh()
            }
        }
    }

    private fun doRefresh() {
        examsRefresh.isRefreshing = true
        GlobalScope.launch {
            examAdapter.exams = Exam.list()
            handler.sendEmptyMessage(0)
        }
    }
}