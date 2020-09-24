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

class QuestionnaireTypeActivity : AppCompatActivity() {

    private lateinit var user: User

    private val category = Category("Kategori 1")
    private val category2 = Category("Kategori 2")
    private val category3 = Category("Kategori 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(USER) ?: throw Throwable("User cannot be null")

        setContentView(R.layout.activity_questionnaire_type)

        supportActionBar?.title = "Hallo, ${user.name}"

        btnSleepQualityQuestionnaire.setOnClickListener {
            Intent(this, QuestionnaireActivity::class.java).apply {
                putExtra(
                    DATA, Questionnaire(
                        getString(R.string.sleep_quality_questionnaire),
                        sleepQualityQuestions
                    )
                )
                putExtra(USER, user)
            }.run {
                startActivity(this)
            }
        }

        btnBerlinQuestionnaire.setOnClickListener {
            Intent(this, QuestionnaireActivity::class.java).apply {
                berlinQuestions.mapIndexed { index, question ->
                    question.number = "${index + 1}"
                }

                putExtra(
                    DATA, Questionnaire(
                        getString(R.string.berlin_questionnaire),
                        berlinQuestions
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

    private var sleepQualityQuestions = listOf(
        Question(
            1,
            "Jam berapa biasanya anda mulai tidur malam ?",
            null,
            listOf(),
            number = "1",
            type = Question.Type.TIME
        ),
        Question(
            2,
            "Berapa lama anda biasanya baru bisa tertidur tiap malam ?",
            null,
            listOf(),
            number = "2",
            type = Question.Type.NUMBER,
            hint = "menit"
        ),
        Question(
            3,
            "Jam berapa biasanya anda bangun pagi ?",
            null,
            listOf(),
            number = "3",
            type = Question.Type.TIME
        ),
        Question(
            4,
            "Berapa lama anda tidur dimalam hari ?",
            null,
            listOf(),
            number = "4",
            type = Question.Type.NUMBER,
            hint = "jam"
        ),
        Question(
            5,
            "Tidak mampu tertidur selama 30 menit sejak berbaring ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5a",
            type = Question.Type.OPTIONAL
        ),
        Question(
            6,
            "Terbangun ditengah malam atau terlalu dini ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5b",
            type = Question.Type.OPTIONAL
        ),
        Question(
            7,
            "Terbangun untuk ke kamar mandi ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5c",
            type = Question.Type.OPTIONAL
        ),
        Question(
            8,
            "Tidak mampu bernafas dengan leluasa ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5d",
            type = Question.Type.OPTIONAL
        ),
        Question(
            9,
            "Batuk dan merokok ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5e",
            type = Question.Type.OPTIONAL
        ),
        Question(
            10,
            "Kedinginan di malam hari ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5f",
            type = Question.Type.OPTIONAL
        ),
        Question(
            11,
            "Kepanasan di malam hari ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5g",
            type = Question.Type.OPTIONAL
        ),
        Question(
            12,
            "Mimpi buruk ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5h",
            type = Question.Type.OPTIONAL
        ),
        Question(
            13,
            "Terasa nyeri ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5i",
            type = Question.Type.OPTIONAL
        ),
        Question(
           14 ,
            "Alasan lain ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "5j",
            type = Question.Type.OPTIONAL
        ),
        Question(
           15 ,
            "Seberapa sering anda menggunakan obat tidur ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "6",
            type = Question.Type.OPTIONAL
        ),
        Question(
            16,
            "Seberapa sering anda mengantuk ketika malakukan aktivitas di siang hari ?",
            null,
            listOf(
                Question.Option("Tidak pernah",0),
                Question.Option("1 x seminggu", 1),
                Question.Option("2 x seminggu", 2),
                Question.Option(">3 x kali sebulan",3)
            ),
            number = "7",
            type = Question.Type.OPTIONAL
        ),
        Question(
            17,
            "Seberapa antusias anda ingin menyelesaikan masalah yang anda hadapi ?",
            null,
            listOf(
                Question.Option("Tidak antusias",0),
                Question.Option("Kecil", 1),
                Question.Option("Sedang", 2),
                Question.Option("Besar",3)
            ),
            number = "8",
            type = Question.Type.OPTIONAL
        ),
        Question(
            18,
            "Pertanyan preintervensi : Bagaimana kualitas tidur anda selama sebulan yang lalu ?",
            null,
            listOf(
                Question.Option("Sangat baik",0),
                Question.Option("Baik", 1),
                Question.Option("Kurang", 2),
                Question.Option("Sangat kurang",3)
            ),
            number = "9a",
            type = Question.Type.OPTIONAL
        ),
        Question(
            19,
            "Pertanyan preintervensi : Bagaimana kualitas tidur anda selama seminggu yang lalu ?",
            null,
            listOf(
                Question.Option("Sangat baik",0),
                Question.Option("Baik", 1),
                Question.Option("Kurang", 2),
                Question.Option("Sangat kurang",3)
            ),
            number = "9b",
            type = Question.Type.OPTIONAL
        )
    )

    private var berlinQuestions = listOf(
        Question(
            1,
            "Apakah anda mendengkur ?",
            category,
            listOf(
                Question.Option("Ya", 1),
                Question.Option("Tidak", 0),
                Question.Option("Tidak Tahu", 0)
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
        Question(
            4,
            "Apakah dengkuran anda mengganggu orang lain ?",
            category,
            listOf(
                Question.Option("Ya", 1),
                Question.Option("Tidak", 0),
                Question.Option("Tidak tahu", 0)
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
        Question(
            8,
            "Apakah anda pernah terkantuk-kantuk atau tertidur saat mengemudi ?",
            category2,
            listOf(
                Question.Option("Ya", 1),
                Question.Option("Tidak", 0)
            ),
            type = Question.Type.OPTIONAL
        ),
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
            ),
            type = Question.Type.OPTIONAL
        ),
        Question(
            10,
            "Apakah tekanan darah anda tinggi ?",
            category3,
            listOf(
                Question.Option("Ya", 0),
                Question.Option("Tidak", 0),
                Question.Option("Tidak tahu", 0)
            ),
            type = Question.Type.OPTIONAL
        )
    )
}