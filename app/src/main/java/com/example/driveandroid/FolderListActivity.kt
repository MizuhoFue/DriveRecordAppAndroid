/*
* 画面名：フォルダ一覧　FolderListActivity
* 更新者：笛木瑞歩
* 　　　　消える前のソースツリーからコピペしたものをビルドを通すため入れた仮のもの　競合した場合は星野さんのもの優先
* 更新日：2020年11月11日
* */
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folder_list.*

class FolderListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_folder_list)


        returnMap.setOnClickListener {
            val intent2 = Intent(this@FolderListActivity, MapsActivity::class.java)
            startActivity(intent2)
        }

        settings.setOnClickListener {
            val settings = Intent(this@FolderListActivity, SupportActivity::class.java)
            startActivity(settings)
        }

        addFolder.setOnClickListener {
            val addFolder = Intent(this@FolderListActivity, FolderCreateActivity::class.java)
            startActivity(addFolder)
        }

    }
}




