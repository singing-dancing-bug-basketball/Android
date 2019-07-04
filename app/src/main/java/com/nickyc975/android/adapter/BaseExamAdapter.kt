package com.nickyc975.android.adapter

import android.app.Activity
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nickyc975.android.R
import com.nickyc975.android.data.Exam

abstract class BaseExamAdapter(protected val activity: Activity, var exams: List<Exam>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val exam = exams[position]
        if (view == null) {
            view = activity.layoutInflater.inflate(R.layout.exam_item, parent, false)
        }

        (view?.findViewById(R.id.exam_title) as TextView).text = exam.title

        val deadline = DateFormat.format("yyyy-MM-dd", exam.deadline)
        (view.findViewById(R.id.exam_ddl) as TextView).text = activity.getString(R.string.deadline, deadline)
        (view.findViewById(R.id.exam_time) as TextView).text = activity.getString(R.string.time, exam.paper.time)
        (view.findViewById(R.id.exam_total_score) as TextView).text = activity.getString(R.string.total_score, exam.paper.totalScore)
        return view
    }

    override fun getItem(position: Int): Exam {
        return exams[position]
    }

    override fun getItemId(position: Int): Long {
        return exams[position].id.toLong()
    }

    override fun getCount(): Int {
        return exams.size
    }
}