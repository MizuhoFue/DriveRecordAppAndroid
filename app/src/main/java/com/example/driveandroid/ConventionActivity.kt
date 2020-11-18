package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_convention.*

class ConventionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convention)

        returnSupport.setOnClickListener {
            val intent = Intent(this@ConventionActivity, SupportActivity::class.java)
            startActivity(intent)
            finish()    //finishしてサポートへ
        }
    }
}