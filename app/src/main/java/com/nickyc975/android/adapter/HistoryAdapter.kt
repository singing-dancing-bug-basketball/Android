package com.nickyc975.android.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nickyc975.android.R
import com.nickyc975.android.model.Data
import com.nickyc975.android.model.Exam
import kotlinx.coroutines.delay

class HistoryAdapter(activity: Activity, histories: List<Exam>): BaseExamAdapter(activity, histories) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        (view.findViewById(R.id.exam_time) as TextView).visibility = View.GONE
        (view.findViewById(R.id.exam_user_core) as TextView).text = activity.getString(R.string.user_score, exams[position].userScore)
        return view
    }

    override suspend fun reload() {
        delay(500)
        exams = Data.exams
    }
}