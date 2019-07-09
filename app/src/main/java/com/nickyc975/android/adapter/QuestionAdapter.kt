package com.nickyc975.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.nickyc975.android.ExamActivity
import com.nickyc975.android.R
import com.nickyc975.android.model.Question

class QuestionAdapter(private val activity: ExamActivity, private val questions: List<Question>): BaseAdapter() {
    companion object {
        class ValuableRadioButton<T>(context: Context): RadioButton(context) {
            var value: T? = null

            constructor(context: Context, text: String, value: T): this(context) {
                this.text = text
                this.value = value
            }
        }
    }

    private val views: Array<View?> = arrayOfNulls(questions.size)

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (views[position] === null) {
            val question = questions[position]
            val view = activity.layoutInflater.inflate(R.layout.question_item, parent, false)
            val radioGroup = view.findViewById<RadioGroup>(R.id.options)
            for ((option, detail) in question.options) {
                radioGroup.addView(ValuableRadioButton(activity, "$option. $detail", option))
            }
            view.findViewById<TextView>(R.id.question).text = "$position. ${question.title}"
            view.findViewById<TextView>(R.id.answer).visibility = View.GONE
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