/*
* 画面：サポート画面
* 変更：ライセンス表示追加
* 更新日：2020年12月21日
* 更新者：笛木
* */
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        license.setOnClickListener {
            //ライセンス表示処理
            val intent = Intent(this, OssLicensesMenuActivity::class.java)
            startActivity(intent)
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