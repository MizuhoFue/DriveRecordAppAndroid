/*
* 画面：フォルダ一覧 FolderList
* 更新者：笛木
* 更新日：2020年12月28日
* 更新内容：ダイアログの色
* */
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
import com.example.driveandroid.Constants.Companion.PARAGRAPH_INFO
import com.example.driveandroid.Constants.Companion.WHERE_ID
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
            adapter.setOnItemClickListener(object : FolderListAdapter.OnItemClickListener {
                override fun onItemClickListener(view: View, deleteId: Int, position: Int) {
                    Log.d("deleteIdとして受け取り", "$deleteId")
                    //ダイアログを出し、OKだったらdeleteする  はいといいえの場所を入れ替え
                    val dialog = AlertDialog.Builder(this@FolderListActivity,R.style.MyAlertColor) //thisだとコンパイルエラー
                        .setMessage("選択した内容を削除してもいいですか?")
                        .setPositiveButton(R.string.no) { _, _ ->
                            Log.d("いいえを選択", "いいえ")
                        }.setNegativeButton(R.string.yes) { _, _ ->
                            //deletePara呼び出し 該当データをParagraphInfoから削除
                            deletePara(deleteId)
                            //deleteFolder呼び出し　該当データをFolderInfoから削除
                            deleteFolder(deleteId)
                            //画面からも該当データを消す
                            folderList.removeAt(position)
                            adapter.notifyDataSetChanged() //変更通知し画面反映
                            if (folderList.isEmpty()) {
                                nothing_message.visibility =
                                    View.VISIBLE //登録されているデータがすべて消された場合はメッセージ表示
                            }
                        }
                        .create() //show()だとクラッシュ
                    dialog.show()
                }
            })
        }

//        //マップへ遷移
//        returnMap.setOnClickListener {
//            val intent = Intent(this@FolderListActivity, MapsActivity::class.java)
//            startActivity(intent)
//        }
        //サポート画面へ遷移
        setting.setOnClickListener {
            val intent = Intent(this@FolderListActivity, SupportActivity::class.java)
            startActivity(intent)
        }
        //フォルダ作成へ遷移　フィニッシュなし
        addFolder.setOnClickListener {
            val intent = Intent(this@FolderListActivity, FolderCreateActivity::class.java)
            startActivity(intent)
        }
    }

    //FolderInfoテーブルを全件セレクト TODO 後ほどDBHelperにまとめる
    private fun selectFolder(): ArrayList<FolderInfo>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderList = ArrayList<FolderInfo>() //FolderInfo型の箱をつくる
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
                    //FolderInfo型クラスを使って結果の値を変数folderInfoに入れる member2から6は念のためnullチェック
                    val folderInfo = FolderInfo(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor?.getString(4),
                        cursor?.getString(5), cursor?.getString(6), cursor?.getString(7),
                        cursor?.getString(8)
                    )
                    folderList.add(folderInfo) //箱に型を入れる
                    cursor.moveToNext()
                }
                nothing_message.visibility = View.GONE //メッセージを消す
            }
            cursor.close()
            return folderList
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }

    /**deletePara folderidを元にParagraphInfoの該当データをdelete
     * @param deleteId
     * */
    private fun deletePara(deleteId: Int) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val whereArgs = arrayOf(deleteId.toString())
            database.delete(PARAGRAPH_INFO, WHERE_ID, whereArgs)
            Log.d("deletePara通ったfolderid", "$deleteId")
        } catch (exception: Exception) {
            Log.d("deletePara", exception.toString())
        }
    }//deletePara閉じ

    /** deleteFolder folderidを元にFolderInfoの該当データをdelete
     * @param deleteId
     * */
    private fun deleteFolder(deleteId: Int) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val whereArgs = arrayOf(deleteId.toString())
            database.delete(FOLDER_INFO, WHERE_ID, whereArgs)
            Log.d("deleteFolder通ったfolderid", "$deleteId")
        } catch (exception: Exception) {
            Log.d("deleteFolder", exception.toString())
        }
    }//deleteFolder閉じ
}