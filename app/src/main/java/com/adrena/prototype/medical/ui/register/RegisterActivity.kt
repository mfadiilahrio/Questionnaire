package com.adrena.prototype.medical.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adrena.prototype.medical.Constants
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.ui.questionnairetype.QuestionnaireType
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var selectedGender = "male"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            Intent(this, QuestionnaireType::class.java).run {
                putExtra(
                    Constants.USER,
                    User(
                        name.text.toString(),
                        selectedGender,
                        age.text.toString().toIntOrNull(),
                        birth_place.text.toString(),
                        dob.text.toString(),
                        address.text.toString(),
                        weight.text.toString().toIntOrNull(),
                        height.text.toString().toIntOrNull(),
                        neck_circumference.text.toString().toIntOrNull()
                    )
                )
                startActivity(this)
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
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

        Toast.makeText(this, selectedGender, Toast.LENGTH_SHORT).show()
    }


}