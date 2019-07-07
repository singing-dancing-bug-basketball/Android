package com.nickyc975.android.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nickyc975.android.R
import com.nickyc975.android.model.Data
import com.nickyc975.android.model.Exam
import kotlinx.coroutines.delay

class ExamAdapter(activity: Activity, exams: List<Exam>): BaseExamAdapter(activity, exams) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        (view.findViewById(R.id.exam_time) as TextView).text = activity.getString(R.string.time, exams[position].time)
        (view.findViewById(R.id.exam_user_core) as TextView).visibility = View.GONE
        return view
    }

    override suspend fun reload() {
        delay(1000)
        exams = Data.exams
    }
}