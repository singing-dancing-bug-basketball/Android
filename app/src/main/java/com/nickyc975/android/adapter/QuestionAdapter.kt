package com.nickyc975.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEach
import com.nickyc975.android.ExamActivity
import com.nickyc975.android.R
import com.nickyc975.android.model.Question

class QuestionAdapter(private val activity: ExamActivity, private val questions: List<Question>): BaseAdapter() {
    companion object {
        class ValuableRadioButton<T>(context: Context): RadioButton(context) {
            var value: T? = null

            constructor(context: Context, value: T, detail: String): this(context) {
                this.value = value
                this.text = detail
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
                val radio = ValuableRadioButton(activity, option, detail)
                radio.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        if (radioGroup.checkedRadioButtonId < 0) {
                            activity.onQuestionChecked()
                        }
                        question.selected = (buttonView as ValuableRadioButton<*>).value as Int?
                    }
                }
                radioGroup.addView(radio)
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

    fun getResult(): Map<Int, Int> {
        return questions.filter { question ->
            question.selected != null
        }.map { question ->
            Pair(question.id, question.selected!!)
        }.toMap()
    }

    fun enableAll() {
        views.forEach { view ->
            view?.findViewById<RadioGroup>(R.id.options)?.forEach { radio ->
                radio.isEnabled = true
            }
        }
    }

    fun disableAll() {
        views.forEach { view ->
            view?.findViewById<RadioGroup>(R.id.options)?.forEach { radio ->
                radio.isEnabled = false
            }
        }
    }
}