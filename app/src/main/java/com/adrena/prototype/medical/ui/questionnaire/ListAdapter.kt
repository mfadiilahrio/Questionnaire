package com.adrena.prototype.medical.ui.questionnaire

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question


class ListAdapter(val list: List<Question>) : RecyclerView.Adapter<QuestionViewHolder>(),
    ItemListener {

    var listener: ItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return QuestionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question: Question = list[position]
        holder.bind(question)

        holder.listener = this
    }

    override fun getItemCount(): Int = list.size

    override fun onClick(question: Question) {
        listener?.onClick(question)
    }

    override fun onOptionClicked(question: Question, selectedOption: Question.Option) {
        listener?.onOptionClicked(question, selectedOption)
    }

}

class QuestionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {

    private var mQuestion: TextView? = null
    private var mOptions: RadioGroup? = null
    var listener: ItemListener? = null

    init {
        mQuestion = itemView.findViewById(R.id.question)
        mOptions = itemView.findViewById(R.id.options)
    }

    fun bind(question: Question) {
        mQuestion?.text = "${question.number}. ${question.question}"

        mOptions?.removeAllViews()

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

            mOptions?.addView(
                radioButton
            )
        }
    }

}