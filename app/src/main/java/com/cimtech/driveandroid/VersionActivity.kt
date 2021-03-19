package com.cimtech.driveandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cimtech.driveandroid.BuildConfig.VERSION_NAME
import kotlinx.android.synthetic.main.activity_version.*

class VersionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version)

        return_home.setOnClickListener {
            finish()
        }
        //version
        version_view.text = VERSION_NAME
    }
}