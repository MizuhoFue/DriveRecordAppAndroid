package com.example.drivefueki1012

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

import kotlinx.android.synthetic.main.convention.*


class Convention : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.convention)

        returnSupport.setOnClickListener {
            val returnSupport = Intent(application, Support::class.java)
            startActivity(returnSupport)
        }
    }
}