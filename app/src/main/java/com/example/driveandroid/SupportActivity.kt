/*
* 画面：サポート画面
* タスク：ライセンス表示処理をいれる
* 変更：アプリ終了処理を削除
* 更新日：2020年11月18日
* 更新者：笛木
* */
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        //設定を開いた画面へ戻る
        back.setOnClickListener {
            finish()    //サポートを閉じる startActivityいらない
        }

        license.setOnClickListener {
            //ライセンス表示処理
        }

        //利用規約
        policies.setOnClickListener {
            val intent = Intent(this@SupportActivity, PolicyActivity::class.java)
            startActivity(intent)
        }
    }
}