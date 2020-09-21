package com.adrena.prototype.medical.ui.questionnairetype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.Constants.Companion.USER
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Category
import com.adrena.prototype.medical.data.model.Question
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.ui.questionnaire.QuestionnaireActivity
import kotlinx.android.synthetic.main.activity_questionnaire_type.*

class QuestionnaireType : AppCompatActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(USER) ?: throw Throwable("User cannot be null")

        setContentView(R.layout.activity_questionnaire_type)

        supportActionBar?.title = "Hallo, ${user.name}"

        berlin_questionnaire.setOnClickListener {
            Intent(this, QuestionnaireActivity::class.java).apply {
                val questions = mutableListOf<Question>()

                val category = Category("Kategori 1", "Kuesioner Berlin")

                questions.add(
                    Question(
                        1,
                        "Apakah anda mendengkur ?",
                        category,
                        listOf(
                            Question.Option("Ya", 1),
                            Question.Option("Tidak", 0),
                            Question.Option("Tidak Tahu", 0)
                        )
                    )
                )

                questions.add(
                    Question(
                        2,
                        "Dengkuran anda ?",
                        category,
                        listOf(
                            Question.Option(
                                "Sedikit lebih nyaring dari bunyi napas biasa (louder than breathing)",
                                1
                            ),
                            Question.Option("Keras seperti biasa", 0),
                            Question.Option("Lebih nyaring dari bicara", 0),
                            Question.Option(
                                "Sangat keras dapat di dengar dari ruangan yang bersebelahan",
                                0
                            )
                        )
                    )
                )

                putExtra(DATA, ArrayList(questions))
            }.run {
                startActivity(this)
            }
        }
    }
}