package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folder_create.*

class FolderCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_create)

        createEnd.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, FolderListActivity::class.java)
            startActivity(intent)
        }
        createEnd.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, DaoActivity::class.java)
            startActivity(intent)
        }
    }
}