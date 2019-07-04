package com.nickyc975.android.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.nickyc975.android.R
import com.nickyc975.android.adapter.Data
import com.nickyc975.android.adapter.ExamAdapter

class ExamsFragment: ToolbarFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_exams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.exam)
        (activity?.findViewById(R.id.exam_list) as ListView).adapter = ExamAdapter(activity as Activity, Data.exams)
    }
}