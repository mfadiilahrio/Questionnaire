package com.adrena.prototype.medical.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.Constants.Companion.USER
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Questionnaire
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.ui.result.ResultActivity

class QuestionnaireActivity : AppCompatActivity(), QuestionnaireFragment.Listener {

    private lateinit var mFragment: QuestionnaireFragment
    private lateinit var user: User
    private lateinit var questionnaire: Questionnaire

    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        user = intent.getParcelableExtra(USER) ?: throw Throwable("User cannot be null")
        questionnaire =
            intent.getParcelableExtra(DATA) ?: throw Throwable("Questionnaire cannot be null")

        if (savedInstanceState != null) {
            mFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as QuestionnaireFragment

        } else {
            mFragment = QuestionnaireFragment.newInstance(
                questionnaire
            )

            supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(android.R.id.content, mFragment)
                .commit()
        }

        mFragment.listener = this
    }

    override fun onStopQuestioner(questionnaire: Questionnaire) {
        Intent(this, ResultActivity::class.java).apply {
            putExtra(DATA, questionnaire)
            putExtra(USER, user)
        }.run {
            startActivity(this)
        }
    }
}