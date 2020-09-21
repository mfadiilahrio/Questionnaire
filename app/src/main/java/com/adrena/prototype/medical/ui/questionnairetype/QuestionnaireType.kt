package com.adrena.prototype.medical.ui.questionnairetype

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.Constants.Companion.USER
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Category
import com.adrena.prototype.medical.data.model.Question
import com.adrena.prototype.medical.data.model.Questionnaire
import com.adrena.prototype.medical.data.model.User
import com.adrena.prototype.medical.ui.questionnaire.QuestionnaireActivity
import kotlinx.android.synthetic.main.activity_questionnaire_type.*

class QuestionnaireType : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(USER) ?: throw Throwable("User cannot be null")

        setContentView(R.layout.activity_questionnaire_type)

        supportActionBar?.title = "Hallo, ${user.name}"

        berlin_questionnaire.setOnClickListener {
            Intent(this, QuestionnaireActivity::class.java).apply {
                val questions = mutableListOf<Question>()

                val category = Category("Kategori 1")
                val category2 = Category("Kategori 2")
                val category3 = Category("Kategori 3")

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
                                0
                            ),
                            Question.Option("Keras seperti biasa", 0),
                            Question.Option("Lebih nyaring dari bicara", 1),
                            Question.Option(
                                "Sangat keras dapat di dengar dari ruangan yang bersebelahan",
                                1
                            )
                        )
                    )
                )

                questions.add(
                    Question(
                        3,
                        "Berapa kali anda mendengkur ?",
                        category,
                        listOf(
                            Question.Option("Hampir setiap minggu",1),
                            Question.Option("3-4 kali seminggu", 1),
                            Question.Option("1-2 kali seminggu", 0),
                            Question.Option("1-2 kali sebulan",0),
                            Question.Option("Tidak pernah atau hampir tidak pernah",0)
                        )
                    )
                )

                questions.add(
                    Question(
                        4,
                        "Apakah dengkuran anda mengganggu orang lain ?",
                        category,
                        listOf(
                            Question.Option("Ya", 1),
                            Question.Option("Tidak", 0),
                            Question.Option("Tidak tahu", 0)
                        )
                    )
                )

                questions.add(
                    Question(
                        5,
                        "Apakah ada orang yang mengatakan bahwa anda berhenti bernafas saat tidur ?",
                        category,
                        listOf(
                            Question.Option("Hampir setiap minggu",1),
                            Question.Option("3-4 kali seminggu", 1),
                            Question.Option("1-2 kali seminggu", 0),
                            Question.Option("1-2 kali sebulan",0),
                            Question.Option("Tidak pernah atau hampir tidak pernah",0)
                        )
                    )
                )

                questions.add(
                    Question(
                        6,
                        "Berapa sering anda merasa lelah atau tidak fit setelah bangun tidur ?",
                        category2,
                        listOf(
                            Question.Option("Hampir setiap minggu",1),
                            Question.Option("3-4 kali seminggu", 1),
                            Question.Option("1-2 kali seminggu", 0),
                            Question.Option("1-2 kali sebulan",0),
                            Question.Option("Tidak pernah atau hampir tidak pernah",0)
                        )
                    )
                )

                questions.add(
                    Question(
                        7,
                        "Pada saat beraktivitas, apakah anda merasa lelah dan tidak segar",
                        category2,
                        listOf(
                            Question.Option("Hampir setiap minggu",1),
                            Question.Option("3-4 kali seminggu", 1),
                            Question.Option("1-2 kali seminggu", 0),
                            Question.Option("1-2 kali sebulan",0),
                            Question.Option("Tidak pernah atau hampir tidak pernah",0)
                        )
                    )
                )

                questions.add(
                    Question(
                        8,
                        "Apakah anda pernah terkantuk-kantuk atau tertidur saat mengemudi ?",
                        category2,
                        listOf(
                            Question.Option("Ya", 1),
                            Question.Option("Tidak", 0)
                        )
                    )
                )

                questions.add(
                    Question(
                        9,
                        "Berapa sering hal tersebut terjadi ?",
                        category2,
                        listOf(
                            Question.Option("Hampir setiap minggu",0),
                            Question.Option("3-4 kali seminggu", 0),
                            Question.Option("1-2 kali seminggu", 0),
                            Question.Option("1-2 kali sebulan",0),
                            Question.Option("Tidak pernah atau hampir tidak pernah",0)
                        )
                    )
                )

                questions.add(
                    Question(
                        10,
                        "Apakah tekanan darah anda tinggi ?",
                        category3,
                        listOf(
                            Question.Option("Ya", 0),
                            Question.Option("Tidak", 0),
                            Question.Option("Tidak tahu", 0)
                        )
                    )
                )

                questions.mapIndexed { index, question ->
                    question.number = index + 1
                }

                putExtra(
                    DATA, Questionnaire(
                        "Kuesioner Berlin",
                        questions
                    )
                )
                putExtra(USER, user)
            }.run {
                startActivity(this)
            }
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}