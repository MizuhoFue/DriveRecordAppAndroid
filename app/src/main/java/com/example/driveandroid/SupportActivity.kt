/*
* 画面：サポート画面
* タスク：アプリ終了をライセンス表示にする
* 変更：アプリ終了処理を削除
* 更新日：2020年11月17日
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

        //ホームへ戻る
        returnHome.setOnClickListener {
            val intent = Intent(this@SupportActivity, FolderListActivity::class.java)
            //クリアタスクしてフォルダ一覧へ
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        //利用規約
        rules.setOnClickListener {
            val intent = Intent(this@SupportActivity, ConventionActivity::class.java)
            startActivity(intent)
        }
    }
}