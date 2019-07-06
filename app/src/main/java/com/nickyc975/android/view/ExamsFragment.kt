package com.nickyc975.android.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nickyc975.android.ExamActivity
import com.nickyc975.android.R
import com.nickyc975.android.model.Data
import com.nickyc975.android.adapter.ExamAdapter

class ExamsFragment: ToolbarFragment() {
    private lateinit var examList: ListView
    private lateinit var examAdapter: ExamAdapter
    private lateinit var examsRefresh: SwipeRefreshLayout

    private var listener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val intent = Intent(this@ExamsFragment.context, ExamActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_exams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.exam)
        examList = view.findViewById(R.id.exam_list)
        examsRefresh = view.findViewById(R.id.exams_refresh)
        examAdapter = ExamAdapter(activity as Activity, listOf())
        examList.adapter = examAdapter
        examsRefresh.setOnRefreshListener {
            examAdapter.exams = Data.exams
            examAdapter.notifyDataSetChanged()
            examsRefresh.isRefreshing = false
        }
        examList.onItemClickListener = listener
    }
}