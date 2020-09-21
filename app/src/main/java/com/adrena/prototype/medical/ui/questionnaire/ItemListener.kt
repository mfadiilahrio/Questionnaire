package com.adrena.prototype.medical.ui.questionnaire

import com.adrena.prototype.medical.data.model.Question

interface ItemListener {
    fun onClick(question: Question)
    fun onOptionClicked(question: Question, selectedOption: Question.Option)
}