package com.example.drivefueki1012

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

import kotlinx.android.synthetic.main.map.*


class Map : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        returnHome.setOnClickListener {
            val intent3 = Intent(application, FolderList::class.java)
            startActivity(intent3)
        }
    }
}