package com.adrena.prototype.medical.ui.result

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.adrena.prototype.medical.Constants
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Questionnaire
import com.adrena.prototype.medical.data.model.User
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var questionnaire: Questionnaire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(Constants.USER) ?: throw Throwable("User cannot be null")
        questionnaire = intent.getParcelableExtra(Constants.DATA)
            ?: throw Throwable("Questionnaire cannot be null")

        setContentView(R.layout.activity_result)

        name.text = getString(R.string.name_x, user.name)
        weight.text = getString(R.string.weight_x_kg, user.weight.toString())
        height.text = getString(R.string.height_x_m, user.height.toString())
        bmi.text = getString(R.string.bmi_x, user.bmi)

        val category1 = questionnaire.questions.filter { it.category.name == "Kategori 1" }
        val category2 = questionnaire.questions.filter { it.category.name == "Kategori 2" }
        val category3 = questionnaire.questions.filter { it.category.name == "Kategori 3" }

        val number10result: Boolean = category3.firstOrNull { it.id == 10 }?.let { q ->
            q.options.any { option ->
                option.answer.contains("YA", true) && option.checked
            }
        } ?: run {
            false
        }

        val category1Result =
            category1.flatMap { it.options.filter { option -> option.checked } }.map { it.point }.sum() > 1
        val category2Result =
            category2.flatMap { it.options.filter { option -> option.checked } }.map { it.point }.sum() > 1
        val category3Result = user.bmi > 30 || number10result

        val riskResult = listOf(
            category1Result,
            category2Result,
            category3Result
        )


        if (riskResult.filter { it }.size > 1) {
            osa_risk_result.text = getString(R.string.high)
            osa_risk_result.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
        } else {
            osa_risk_result.text = getString(R.string.low)
            osa_risk_result.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        }

        done.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}