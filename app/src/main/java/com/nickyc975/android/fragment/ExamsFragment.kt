package com.nickyc975.android.fragment

import android.app.Activity
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.nickyc975.android.R
import com.nickyc975.android.adapter.ExamAdapter
import com.nickyc975.android.data.Exam
import com.nickyc975.android.data.Paper
import com.nickyc975.android.data.Question
import java.util.*

class ExamsFragment: ToolbarFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_exams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.exam)
        val exams = listOf(Exam(
            1,
            Paper(
                1, 120, 100.0, listOf(Question(
                    1, "a", "b", mapOf()
                ))
            ),
            "c", Date()
        ))
        (activity?.findViewById(R.id.exam_list) as ListView).adapter = ExamAdapter(activity as Activity, exams)
    }
}