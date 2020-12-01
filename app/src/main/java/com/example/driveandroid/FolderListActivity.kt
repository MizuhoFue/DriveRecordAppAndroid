/*
* 画面：フォルダ一覧 FolderList
* 更新者：笛木
* 更新日：2020年12月1日
* 内容：小林さん指導の元データ型クラスを使ってセレクト処理修正、カーソルクローズ処理追加
* */
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
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
        //中身を空にする
        //FolderInfo全件セレクト
        val folderList = selectFolder()
        //nullチェックを通ったら
        folderList?.let {
            //二つの配列を表示させる
            val adapter = FolderListAdapter(it)
            val layoutManager = LinearLayoutManager(this)
            // アダプターとレイアウトマネージャーをセット folderListはRecyclerViewのid
            folderListView.layoutManager = layoutManager
            folderListView.adapter = adapter
            folderListView.setHasFixedSize(true)
        }

        //マップへ遷移
        returnMap.setOnClickListener {
            val intent = Intent(this@FolderListActivity, MapsActivity::class.java)
            startActivity(intent)
        }
        //サポート画面へ遷移
        setting.setOnClickListener {
            val intent = Intent(this@FolderListActivity, SupportActivity::class.java)
            startActivity(intent)
        }
        //フォルダ作成へ遷移　フィニッシュなし
        addFolder.setOnClickListener {
            val intent = Intent(this@FolderListActivity, FolderCreateActivity::class.java)
            //intent.putExtra(Constants.EXTRA_FOLDERID, folderid)
            startActivity(intent)
        }
    }

    //FolderInfoテーブルを全件セレクト　戻り値は日付配列とタイトル配列 TODO 後ほどDBHelperにまとめる
    fun selectFolder(): ArrayList<FolderInfo>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderList = ArrayList<FolderInfo>()
            //select文　FolderInfoテーブルセレクト
            val sql = "SELECT * FROM FolderInfo" //sql文OK
            val cursor =
                database.query(FOLDER_INFO, null, null, null, null, null, null)
            Log.d("登録件数", "${cursor.count}")

            //クエリ実行 cursorで結果セット受け取り
            if (cursor.count > 0) {
                Log.d("テーブルの登録件数", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    //folderInfoのdateクラスをfolderInfoに入れる
                    val folderInfo = FolderInfo(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getString(8)
                    )
                    folderList.add(folderInfo)
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return folderList
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }
}