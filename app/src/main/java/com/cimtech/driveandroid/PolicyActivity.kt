package com.cimtech.driveandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify.WEB_URLS
import android.text.util.Linkify.addLinks
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        addLinks(policy_8_url, WEB_URLS)

        //ナビゲーションアイテムのリスナー
        close_button.setOnClickListener {
            finish()    //finishしてサポートへ
        }

        //リンクからブラウザにとぶ処理
        val policy: TextView = this.policy_8_url
        // policyをclickableにする
        val mMethod = LinkMovementMethod.getInstance()
        policy.movementMethod = mMethod
        val url = "https://www.cimtech.co.jp/privacy/"
        // urlをhtmlを使ってリンクテキストに埋め込む
        val link: CharSequence =
            Html.fromHtml("<a href=\"$url\">https://www.cimtech.co.jp/privacy/</a>")
        policy.text = link
    }

    // textViewのリンクが押されるとここに入ってくる
    override fun startActivity(intent: Intent?) {
        // アラートダイアログから飛んできたときとそれ以外のときを判別
        val alertFlg = intent?.getBooleanExtra("alert", false)

        // アラートダイアログから飛んできてる場合はそのままブラウザを開かせる
        if (alertFlg == true) {
            // 呼ばれるとブラウザが開く
            super.startActivity(intent)
            return
        }

        // ダイアログを表示する
        val urlData = intent?.data
        AlertDialog.Builder(this, R.style.MyAlertColor)
            .setMessage(R.string.url_dialog)
            .setNegativeButton(R.string.no, null)
            .setPositiveButton(R.string.yes) { _, _ ->
                val urlStr = Uri.parse(urlData.toString())
                val intent = Intent(Intent.ACTION_VIEW, (Uri.parse(urlStr.toString())))
                intent.putExtra("alert", true)
                startActivity(intent)
            }
            .show()
    }
}