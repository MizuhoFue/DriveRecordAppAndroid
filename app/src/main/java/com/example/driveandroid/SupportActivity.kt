/*
* 画面：サポート画面
* 変更：ライセンス表示追加
* 更新日：2020年12月21日
* 更新者：笛木
* */
package com.example.driveandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_policy.*
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        guide.setOnClickListener {
            // ダイアログを表示する
            val dialog = AlertDialog.Builder(this, R.style.MyAlertColor)
                .setMessage(R.string.url_dialog)
                .setPositiveButton(R.string.yes) { _, _ ->
                    val urlStr =
                        Uri.parse("https://documentcloud.adobe.com/link/review?uri=urn:aaid:scds:US:ef60fdff-7982-4cb4-af38-89c6f35e6049")
                    val intent = Intent(Intent.ACTION_VIEW, urlStr)
                    startActivity(intent)
                }
                .setNegativeButton(R.string.no) { _, _ -> // いいえ
                    Intent(this@SupportActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        license.setOnClickListener {
            //ライセンス表示処理
            val intent = Intent(this, OssLicensesMenuActivity::class.java)
            startActivity(intent)
        }

        policies.setOnClickListener {
            val intent = Intent(this@SupportActivity, PolicyActivity::class.java)
            startActivity(intent)
        }

        version.setOnClickListener {
            val intent = Intent(this@SupportActivity, VersionActivity::class.java)
            startActivity(intent)
        }

        return_home.setOnClickListener {
            finish()
        }
    }
}