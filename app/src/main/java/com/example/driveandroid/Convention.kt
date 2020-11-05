package com.example.driveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.convention.*

class ConventionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.convention)

        returnSupport.setOnClickListener {
            val intent = Intent(this@ConventionActivity, SupportActivity::class.java)
            startActivity(intent)
        }
    }
}