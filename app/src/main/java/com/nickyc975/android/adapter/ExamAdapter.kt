package com.nickyc975.android.adapter

import android.app.Activity
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nickyc975.android.R
import com.nickyc975.android.data.Exam

class ExamAdapter(private val activity: Activity, var exams: List<Exam>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val exam = exams[position]
        if (view == null) {
            view = activity.layoutInflater.inflate(R.layout.exam_item, parent, false)
        }

        (view?.findViewById(R.id.exam_title) as TextView).text = exam.title
        (view.findViewById(R.id.exam_ddl) as TextView).text = DateUtils.formatDateTime(
            activity, exam.deadline.time, DateUtils.FORMAT_ABBREV_ALL
        )
        (view.findViewById(R.id.exam_time) as TextView).text = String.format("%d ${activity.resources.getString(R.string.minute)}", exam.paper.time)
        (view.findViewById(R.id.exam_total_score) as TextView).text = String.format("%.2f", exam.paper.totalScore)
        (view.findViewById(R.id.exam_user_core) as TextView).text = String.format("%.2f", exam.userScore)
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