package com.example.driveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.folder_create.*



class FolderCreate : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.folder_create)

        createEnd.setOnClickListener {
            val intent = Intent(application, FolderList::class.java)
            startActivity(intent)
        }
            createEnd.setOnClickListener {
                val touroku = Intent(this@FolderCreate, DaoActivity::class.java)
                startActivity(touroku)
            }
    }
}