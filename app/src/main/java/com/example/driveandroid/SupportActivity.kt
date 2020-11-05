package com.example.driveandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        returnHome.setOnClickListener {
            val intent = Intent(this@SupportActivity, FolderListActivity::class.java)
            startActivity(intent)
        }

        rules.setOnClickListener {
            val intent = Intent(this@SupportActivity, ConventionActivity::class.java)
            startActivity(intent)
        }

        setupViews()
    }

    private fun setupViews() {
        // appliEndがタップされたときにダイアログを表示
        appliEnd.setOnClickListener {
            // BuilderからAlertDialogを作成
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.finish_message) // タイトル
                .setPositiveButton(R.string.yes) { dialog, which -> // OK
                    finish()
                    moveTaskToBack(true)
                }
                .setNegativeButton(R.string.no) { dialog, which -> //no
                    Intent(this@SupportActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }
    }
}