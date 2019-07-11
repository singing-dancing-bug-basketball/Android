package com.nickyc975.android.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.nickyc975.android.HistoryActivity
import com.nickyc975.android.R
import com.nickyc975.android.model.Question

class HistoryQuestionAdapter(private val activity: HistoryActivity, private val questions: List<Question>): BaseAdapter() {
    private val views: Array<View?> = arrayOfNulls(questions.size)

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (views[position] === null) {
            val question = questions[position]
            val view = activity.layoutInflater.inflate(R.layout.question_item, parent, false)
            val radioGroup = view.findViewById<RadioGroup>(R.id.options)
            for ((option, detail) in question.options) {
                val radio = RadioButton(activity)
                radio.text = "$option. $detail"
                radio.isEnabled = false
                if (option == question.selected) {
                    radio.isChecked = true
                }
                radioGroup.addView(radio)
            }
            view.findViewById<TextView>(R.id.question).text = "$position. ${question.title}"
            view.findViewById<TextView>(R.id.answer).text = "${question.answer}. ${question.options[question.answer]}"
            views[position] = view
        }
        return views[position]!!
    }

    override fun getItem(position: Int): Question {
        return questions[position]
    }

    override fun getItemId(position: Int): Long {
        return questions[position].id.toLong()
    }

    override fun getCount(): Int {
        return questions.size
    }
}