package com.example.driveandroid
/*
*画面名：金額入力画面　使用した金額や使用用途の入力・登録を行う　ひとまずMainActivityを参考につくって部品化　
* 入力された値を変数に入れてSQL実行
*遷移先：FolderList,FolderDetail ダイアログで選択
*
* */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MoneyInsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_insert)
    }




}