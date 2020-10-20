package com.example.drivefueki1012

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FolderDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)



    //入力完了ボタン（inputComp）を遷移用ボタンとして設定
    val share: Button =findViewById<Button>(R.id.share)

    share.setOnClickListener{//押したら
        //Intent作成 MainActivityの金額入力からフォルダー詳細に遷移
        val intent3 = Intent(this@FolderDetail,DaoActivity::class.java)
        startActivity(intent3)
    }

    }




}