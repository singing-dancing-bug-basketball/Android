package com.nickyc975.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.nickyc975.android.ExamActivity
import com.nickyc975.android.R
import com.nickyc975.android.model.Question

class QuestionAdapter(private val activity: ExamActivity, private var questions: List<Question>): BaseAdapter() {
    companion object {
        class ValuableRadioButton<T>(context: Context): RadioButton(context) {
            var value: T? = null

            constructor(context: Context, text: String, value: T): this(context) {
                this.text = text
                this.value = value
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val question = questions[position]
        val view: View = convertView ?: activity.layoutInflater.inflate(
            R.layout.question_item, parent, false
        )

        view.findViewById<TextView>(R.id.question).text = "$position. ${question.title}"
        val radioGroup = view.findViewById<RadioGroup>(R.id.options)
        radioGroup.removeAllViews()
        for ((key, value) in question.options) {
            val radio = ValuableRadioButton(activity, "$key. $value", key)
            radioGroup.addView(radio)
            if (radio.value == question.selected) {
                radioGroup.check(radio.id)
            }
            radio.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    question.selected = (buttonView as ValuableRadioButton<*>).value as String
                }
            }
        }

        view.findViewById<TextView>(R.id.answer).visibility = View.GONE

        return view
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