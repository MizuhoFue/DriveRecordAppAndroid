package com.example.driveandroid

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
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
}