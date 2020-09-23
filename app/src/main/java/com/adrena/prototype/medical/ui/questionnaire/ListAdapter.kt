package com.adrena.prototype.medical.ui.questionnaire

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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