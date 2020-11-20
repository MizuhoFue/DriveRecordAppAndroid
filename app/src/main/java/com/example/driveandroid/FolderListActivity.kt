/*
* 画面：フォルダ一覧 FolderList
* 更新者：笛木
* 更新日：2020年11月18日
* */
package com.example.driveandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_folder_list.*

class FolderListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_folder_list)
    }

    //Resume処理
    override fun onResume() {
        super.onResume()

        // 表示するテキスト配列を作る [テキスト１, テキスト２, ....]
        val list = Array<String>(10) { "タイトル$it" }
        val adapter = FolderListAdapter(list)
        val layoutManager = LinearLayoutManager(this)

        // アダプターとレイアウトマネージャーをセット
        folderList.layoutManager = layoutManager
        folderList.adapter = adapter
        folderList.setHasFixedSize(true)

        //マップへ遷移
        returnMap.setOnClickListener {
            val intent = Intent(this@FolderListActivity, MapsActivity::class.java)
            startActivity(intent)
        }

        //フォルダ作成へ遷移　フィニッシュなし　セレクトして詳細を表示するにはここでfolderidを渡す必要あり？
        addFolder.setOnClickListener {
            val intent = Intent(this@FolderListActivity, FolderCreateActivity::class.java)
            //intent.putExtra(Constants.EXTRA_FOLDERID, folderid)
            startActivity(intent)
        }

        setting.setOnClickListener {
            val intent = Intent(this@FolderListActivity, SupportActivity::class.java)
            startActivity(intent)
        }
    }
}