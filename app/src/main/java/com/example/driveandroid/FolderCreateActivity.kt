package com.example.driveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
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