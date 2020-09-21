package com.adrena.prototype.medical.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question
import com.adrena.prototype.medical.ui.result.ResultActivity

class QuestionnaireActivity : AppCompatActivity(), QuestionnaireFragment.Listener {

    private lateinit var mFragment: QuestionnaireFragment

    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        if (savedInstanceState != null) {
            mFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as QuestionnaireFragment

        } else {
            mFragment = QuestionnaireFragment.newInstance(
                intent?.getParcelableArrayListExtra(DATA) ?: throw Throwable("List cannot be null")
            )

            supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(android.R.id.content, mFragment)
                .commit()
        }

        mFragment.listener = this
    }

    override fun onStopQuestioner(questions: ArrayList<Question>) {
        Intent(this, ResultActivity::class.java).apply {
            putParcelableArrayListExtra(DATA, questions)
        }.run {
            startActivity(this)
        }
    }
}