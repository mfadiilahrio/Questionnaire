package com.adrena.prototype.medical.ui.questionnaire

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class QuestionActionSheet : BottomSheetDialogFragment() {

    private lateinit var mTvQuestion: TextView
    private lateinit var mEdtAnswer: TextInputLayout
    private lateinit var mBtnAnswer: Button
    private lateinit var mQuestion: Question

    var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_question_action_sheet, container, false)

        mQuestion = arguments?.getParcelable(DATA) ?: throw Throwable("Question cannot be null")

        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val ivClose: ImageView = view.findViewById(R.id.ivClose)
        mTvQuestion = view.findViewById(R.id.tvQuestion)
        mEdtAnswer = view.findViewById(R.id.edtAnswer)
        mBtnAnswer = view.findViewById(R.id.btnAnswer)

        tvTitle.text = getString(R.string.number_x, mQuestion.number)

        if (mQuestion.type == Question.Type.NUMBER) {
            mEdtAnswer.editText?.inputType = InputType.TYPE_CLASS_NUMBER
        }
        mTvQuestion.text = mQuestion.question
        mEdtAnswer.editText?.setText(mQuestion.answer)
        mEdtAnswer.hint = mQuestion.hint

        ivClose.setOnClickListener {
            dismiss()
        }

        mEdtAnswer.editText?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                onAnswerDone()
                return@OnKeyListener true
            }
            false
        })

        mBtnAnswer.setOnClickListener {
            onAnswerDone()
        }

        return view
    }

    private fun onAnswerDone() {
        mQuestion.answer = mEdtAnswer.editText?.text.toString()
        listener?.onAnswerDone(mQuestion)
        dismiss()
    }

    companion object {
        fun newInstance(
            question: Question
        ): QuestionActionSheet {
            val fragment =
                QuestionActionSheet()

            val args = Bundle().apply {
                putParcelable(DATA, question)
            }

            fragment.arguments = args

            return fragment
        }
    }

    interface Listener {
        fun onAnswerDone(question: Question)
    }
}

