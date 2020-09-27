package com.adrena.prototype.medical.ui.result

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.adrena.prototype.medical.Constants
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Questionnaire
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.safeLet
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var questionnaire: Questionnaire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(Constants.USER) ?: throw Throwable("User cannot be null")
        questionnaire = intent.getParcelableExtra(Constants.DATA)
            ?: throw Throwable("Questionnaire cannot be null")

        setContentView(R.layout.activity_result)

        tvName.text = getString(R.string.name_x, user.name)
        tvWeight.text = getString(R.string.weight_x_kg, user.weight.toString())
        tvHeight.text = getString(R.string.height_x_m, user.height.toString())
        tvBmi.text = getString(R.string.bmi_x, user.bmi)

        if (questionnaire.name == getString(R.string.berlin_questionnaire)) {
            val category1 =
                questionnaire.questions.filter { it.category?.name == getString(R.string.category_1) }
            val category2 =
                questionnaire.questions.filter { it.category?.name == getString(R.string.category_2) }
            val category3 =
                questionnaire.questions.filter { it.category?.name == getString(R.string.category_3) }

            val number10result: Boolean = category3.firstOrNull { it.id == 10 }?.let { q ->
                q.options.any { option ->
                    option.answer.contains("YA", true) && option.checked
                }
            } ?: run {
                false
            }

            val category1Result =
                category1.flatMap { it.options.filter { option -> option.checked } }
                    .map { it.point }.sum() > 1
            val category2Result =
                category2.flatMap { it.options.filter { option -> option.checked } }
                    .map { it.point }.sum() > 1
            val category3Result = user.bmi > 30 || number10result

            val riskResult = listOf(
                category1Result,
                category2Result,
                category3Result
            )


            if (riskResult.filter { it }.size > 1) {
                tvResult.text = getString(R.string.high)
                tvResult.setTextColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
            } else {
                tvResult.text = getString(R.string.low)
                tvResult.setTextColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_green_light
                    )
                )
            }
        } else {
            tvResultTitle.text = getString(R.string.skor)

            val score2 = questionnaire.questions.filter { it.number == "2" || it.number == "5a" }
                .flatMap { it.options.filter { option -> option.checked } }
                .map { it.point }.sum()
            val score3 =
                questionnaire.questions.first { it.number == "4" }.answer?.toDoubleOrNull() ?: 1.0
            val sleep = questionnaire.questions.first { it.number == "1" }.answer
            val wakeUp = questionnaire.questions.first { it.number == "3" }.answer
            val score5 =
                questionnaire.questions.filter { it.number == "5b" || it.number == "5c" || it.number == "5d" || it.number == "5e" || it.number == "5f" || it.number == "5g" || it.number == "5h" || it.number == "5i" || it.number == "5j" }
                    .flatMap { it.options.filter { option -> option.checked } }
                    .map { it.point }.sum()

            val longInBed = getLongInBed(sleep, wakeUp)

            val sleepEfficiency = (score3 / longInBed) * 100
            val score7 = questionnaire.questions.filter { it.number == "7" || it.number == "8" }
                .flatMap { it.options.filter { option -> option.checked } }
                .map { it.point }.sum()

            val score1total =
                questionnaire.questions.filter { it.number == "9a" || it.number == "9b" }
                    .flatMap { it.options.filter { option -> option.checked } }
                    .map { it.point }.sum()
            val score2Total = when {
                score2 <= 0 -> {
                    0
                }
                score2 in 1..2 -> {
                    1
                }
                score2 in 3..4 -> {
                    2
                }
                else -> {
                    3
                }
            }
            val score3Total = when {
                score3 > 7.0 -> {
                    0
                }
                score3 in 6.0..7.0 -> {
                    1
                }
                score3 == 5.0 -> {
                    2
                }
                else -> {
                    0
                }
            }
            val score4Total = when {
                sleepEfficiency > 85.0 -> {
                    0
                }
                sleepEfficiency in 75.0..84.0 -> {
                    1
                }
                sleepEfficiency in 65.0..74.0 -> {
                    2
                }
                else -> {
                    0
                }
            }
            val score5Total = when {
                score5 <= 0 -> {
                    0
                }
                score5 in 1..9 -> {
                    1
                }
                score5 in 10..18 -> {
                    2
                }
                else -> {
                    3
                }
            }
            val score6total =
                questionnaire.questions.first { it.number == "6" }.options.filter { option -> option.checked }
                    .map { it.point }.sum()
            val score7Total = when {
                score7 <= 0 -> {
                    0
                }
                score7 in 1..2 -> {
                    1
                }
                score7 in 3..4 -> {
                    2
                }
                else -> {
                    3
                }
            }

            val scoreTotal = listOf(
                score1total,
                score2Total,
                score3Total,
                score4Total,
                score5Total,
                score6total,
                score7Total
            ).sum()

            tvResult.textSize = 42f
            tvResult.text = scoreTotal.toString()
        }

        btnDone.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getLongInBed(sleep: String?, wakeUp: String?): Double {
        var longInBed = 0.0

        safeLet(sleep, wakeUp) { s, w ->
            val simpleDateFormat = SimpleDateFormat("hh:mm a")

            val date1 = simpleDateFormat.parse(s)
            val date2 = simpleDateFormat.parse(w)

            safeLet(date1, date2) { d1, d2 ->
                val difference: Long = d2.time - d1.time
                val days = (difference / (1000 * 60 * 60 * 24)).toInt()
                var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60))
//                val min =
//                    (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
                hours = if (hours < 0) -hours else hours
                longInBed = hours.toDouble()
            }
        }

        return longInBed
    }
}