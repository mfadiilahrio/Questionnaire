package com.adrena.prototype.medical.ui.register

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adrena.prototype.medical.Constants
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.ui.questionnairetype.QuestionnaireType
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    private var dateListener: OnDateSetListener? = null
    private var selectedGender = "male"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val myCalendar = Calendar.getInstance()
        dateListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date: TextView = findViewById(R.id.dob)
            val myFormat = getString(R.string.date_format)
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            date.text = sdf.format(myCalendar.time)
        }

        dob.setOnClickListener {
            DatePickerDialog(
                this, dateListener,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        register.setOnClickListener {
            if (name.text.isNotEmpty() && weight.text.isNotEmpty() && height.text.isNotEmpty()) {
                Intent(this, QuestionnaireType::class.java).run {

                    val weight = weight.text.toString().toIntOrNull() ?: 0
                    val height = height.text.toString().toIntOrNull() ?: 0
                    val heightInMeter: Double = height/100.0
                    val bmi: Double = weight / (heightInMeter * heightInMeter)

                    putExtra(
                        Constants.USER,
                        User(
                            name.text.toString(),
                            selectedGender,
                            age.text.toString().toIntOrNull(),
                            birth_place.text.toString(),
                            dob.text.toString(),
                            address.text.toString(),
                            weight,
                            height,
                            neck_circumference.text.toString().toIntOrNull(),
                            bmi
                        )
                    )
                    startActivity(this)
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.information))
                    setMessage(getString(R.string.fill_column_message))
                    setPositiveButton(getString(R.string.ok)) { _, _ ->

                    }
                }.show()
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.male ->
                    if (checked) {
                        selectedGender = male.text.toString()
                    }
                R.id.female ->
                    if (checked) {
                        selectedGender = female.text.toString()
                    }
            }
        }
    }


}