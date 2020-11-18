package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_convention.*

class ConventionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convention)

        //タイトルラベルの左側のナビゲーションアイテムの設置
        drive_toolbar.setNavigationIcon(android.R.drawable.ic_menu_directions)
        //ナビゲーションアイテムのリスナー
        drive_toolbar.setNavigationOnClickListener {
            val intent = Intent(this@ConventionActivity, SupportActivity::class.java)
            startActivity(intent)
        }
    }
}