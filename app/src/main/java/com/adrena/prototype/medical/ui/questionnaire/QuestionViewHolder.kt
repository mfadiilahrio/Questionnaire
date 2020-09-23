package com.adrena.prototype.medical.ui.questionnaire

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question

class QuestionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.question_item, parent, false)) {

    private var mTvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
    private var mRgOptions: RadioGroup = itemView.findViewById(R.id.RgOptions)
    private var mEdtAnswer: EditText = itemView.findViewById(R.id.edtAnswer)
    private var mBtnAnswer: Button = itemView.findViewById(R.id.btnAnswer)

    var listener: ItemListener? = null

    @SuppressLint("SetTextI18n")
    fun bind(question: Question) {
        mTvQuestion.text = "${question.number}. ${question.question}"

        mRgOptions.removeAllViews()

        question.options.forEach {
            val radioButton = RadioButton(itemView.context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = it.answer
                tag = it.answer
                isChecked = it.checked

                setOnClickListener { _ ->
                    listener?.onOptionClicked(question, it)
                }
            }

            mRgOptions.addView(
                radioButton
            )
        }

        if (question.options.isNullOrEmpty()) {
            mEdtAnswer.visibility = if (question.answer.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            mBtnAnswer.visibility = View.VISIBLE
        } else {
            mEdtAnswer.visibility = View.GONE
            mBtnAnswer.visibility = View.GONE
        }

        mEdtAnswer.setText(question.answer)

        mBtnAnswer.setOnClickListener {
            listener?.onClick(question)
        }
    }

}