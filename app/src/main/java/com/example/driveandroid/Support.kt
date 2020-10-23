package com.example.driveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.drivefueki1012.R

import kotlinx.android.synthetic.main.support.*

class Support : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support)

        returnHome.setOnClickListener {
            val support = Intent(application, FolderList::class.java)
            startActivity(support)
        }

        kiyaku.setOnClickListener {
            val kiyaku = Intent(application, Convention::class.java)
            startActivity(kiyaku)
        }

        appliEnd.setOnClickListener{
            val finish = Intent(this@Support,FinishActivity::class.java)
        }

    }
}