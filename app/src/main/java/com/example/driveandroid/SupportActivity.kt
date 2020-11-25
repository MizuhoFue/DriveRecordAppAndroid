/*
* 画面：サポート画面
* タスク：ライセンス表示処理をいれる
* 変更：設定ボタンのid変更
* 更新日：2020年11月25日
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

        license.setOnClickListener {
            //ライセンス表示処理
        }

        policies.setOnClickListener {
            val intent = Intent(this@SupportActivity, PolicyActivity::class.java)
            startActivity(intent)
        }
        //タイトルラベルの左側のナビゲーションアイテムの設置 ツールバーのid変えました
        drive_toolbars.setNavigationIcon(android.R.drawable.ic_menu_directions)
        //ナビゲーションアイテムのリスナー
        drive_toolbars.setNavigationOnClickListener {
            finish()
        }
    }
}