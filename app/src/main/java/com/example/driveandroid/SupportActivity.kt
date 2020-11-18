package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_support.drive_toolbar as drive_toolbar1

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        rules.setOnClickListener {
            val intent = Intent(this@SupportActivity, ConventionActivity::class.java)
            startActivity(intent)
        }
//        setupViews()

        //タイトルラベルの左側のナビゲーションアイテムの設置
        drive_toolbar.setNavigationIcon(android.R.drawable.ic_menu_directions)
        //ナビゲーションアイテムのリスナー
        drive_toolbar.setNavigationOnClickListener {
            val intent = Intent(this@SupportActivity, FolderListActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun setupViews() {
    // appliEndがタップされたときにダイアログを表示
//        appliEnd.setOnClickListener {
//            // BuilderからAlertDialogを作成
//            val dialog = AlertDialog.Builder(this)
//                .setTitle(R.string.finish_message) // タイトル
//                .setPositiveButton(R.string.yes) { dialog, which -> // OK
//                    finish()
//                    moveTaskToBack(true)
//                }
//                .setNegativeButton(R.string.no) { dialog, which -> //no
//                    Intent(this@SupportActivity, this::class.java)
//                }
//                .create()
//            // AlertDialogを表示
//            dialog.show()
//        }
//    }
}