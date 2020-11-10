//画面：フォルダ詳細
//更新日：2020年11月10日
//更新者：笛木瑞歩
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folder_detail.*

class FolderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)
        
        home.setOnClickListener {//押したら
            //Intent作成 MainActivityの金額入力からフォルダー詳細に遷移
            val intent = Intent(
                this@FolderDetailActivity,
                FolderListActivity::class.java
            )
            startActivity(intent)
        }
    }
}