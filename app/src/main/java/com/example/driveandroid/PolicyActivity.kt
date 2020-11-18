package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)

        returnSupport.setOnClickListener {
            val intent = Intent(this@PolicyActivity, SupportActivity::class.java)
            startActivity(intent)
            finish()    //finishしてサポートへ
        }
    }
}