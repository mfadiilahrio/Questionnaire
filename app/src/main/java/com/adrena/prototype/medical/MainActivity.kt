package com.adrena.prototype.medical

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adrena.prototype.medical.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, RegisterActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}