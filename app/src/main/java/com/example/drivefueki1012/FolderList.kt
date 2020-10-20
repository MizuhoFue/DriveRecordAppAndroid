package com.example.drivefueki1012

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.folder_list.*

class FolderList : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.folder_list)


        returnMap.setOnClickListener {
            val intent2 = Intent(application, Map::class.java)
            startActivity(intent2)
        }

        settings.setOnClickListener {
            val settings = Intent(application, Support::class.java)
            startActivity(settings)
        }

        addFolder.setOnClickListener {
            val addFolder = Intent(application, FolderCreate::class.java)
            startActivity(addFolder)
        }

    }
}