package com.example.driveandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.BuildConfig.VERSION_CODE
import kotlinx.android.synthetic.main.activity_version.*

class VersionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version)

        return_home.setOnClickListener {
            finish()
        }
        //version
        version_view.text = VERSION_CODE.toString()
    }
}