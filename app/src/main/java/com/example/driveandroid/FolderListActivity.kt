/*
* 画面：フォルダ一覧 FolderList
* 更新者：笛木
* 更新日：2020年11月25日
* 内容：フォーマット整理
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

    //DB用日付配列初期化 finish()から戻ってきた時中身が空にならない
    private var dates: ArrayList<Int> = arrayListOf()

    //DB用タイトル配列初期化
    private var titles: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_folder_list)
    }

    //Resume処理
    override fun onResume() {
        super.onResume()
        //中身を空にする
        dates = arrayListOf()
        titles = arrayListOf()
        //FolderInfo全件セレクト
        val selectResult = selectFolder()
        //戻り値をそれぞれ配列に入れ　
        val dateList = selectResult.first
        val titleList = selectResult.second

        //二つの配列を表示させる
        val adapter = FolderListAdapter(dateList, titleList)
        val layoutManager = LinearLayoutManager(this)

        // アダプターとレイアウトマネージャーをセット folderListはRecyclerViewのid
        folderList.layoutManager = layoutManager
        folderList.adapter = adapter
        folderList.setHasFixedSize(true)

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

    //FolderInfoテーブルを全件セレクト　戻り値は日付配列とタイトル配列
    fun selectFolder(): Pair<ArrayList<Int>, ArrayList<String>> {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            //select文　FolderInfoテーブルセレクト
            val sql = "SELECT date, title FROM FolderInfo" //sql文OK
            val cursor =
                database.query(FOLDER_INFO, arrayOf("date", "title"), null, null, null, null, null)
            Log.d("登録件数", "${cursor.count}")

            //クエリ実行 cursorで結果セット受け取り
            if (cursor.count > 0) {
                Log.d("テーブルの登録件数", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    //各要素（date,titleごとに配列にわける）
                    dates.add(cursor.getInt(0))//日付
                    titles.add(cursor.getString(1))//タイトル
                    cursor.moveToNext()
                }
            }
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
        }
        //二つの配列を返す
        return Pair(dates, titles)//arrayFolderId
    }
}