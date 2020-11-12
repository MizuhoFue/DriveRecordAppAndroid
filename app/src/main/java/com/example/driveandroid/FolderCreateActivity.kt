package com.example.driveandroid
/*FolderCreateActivity フォルダ作成画面
* folderid　一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
*   日付は入力した時点でフォーマットをかけてinsert フォーマットはyyyy/MM/dd
* */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_folder_create.*

class FolderCreateActivity : AppCompatActivity() {

    //DB用変数用意
    private var folderid = 0 //セレクトした後に入れる
    private var title = "" //タイトル
    private var date = 0 //日付　

    //MoneyInsertActivityに渡すfolderid準備 以前のidが残らないようにFolderCreateを破棄する必要？
    companion object {
        const val folderIdData = "com.example.driveandroid.folderIdData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_create)

        createEnd.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, FolderListActivity::class.java)
            startActivity(intent)
        }
        //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
        money.setOnClickListener {
            //入力チェック、エラーがある場合はダイアログ表示
            //まずinsertする
            folderid=2
            Log.d("folderidの値確認","${folderid}")
        val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)
            intent.putExtra(folderIdData,folderid)
            startActivity(intent)
        }
    }
}
//
//        //insert用文FolderInfo用　
//        fun insertData(
//            folderid: Int, title: String, date: Int, member1: String,
//            member2: String, member3: String, member4: String, member5: String, member6: String
//        ) {
//            try {
//                val dbHelper = DriveDBHelper(applicationContext, dbName, null, dbVersion)
//                val database = dbHelper.writableDatabase
//                //初期データをinsertしたい
//                val values = ContentValues()
//                values.put("title", “タイトル”)
//                values.put("20201019", date)
//                values.put("太郎", member1) //逆
//                //クエリ実行？
//                database.insertOrThrow(tableName1, null, values)
//
//            } catch (exception: Exception) {
//                Log.e("InsertData", exception.toString())
//            }
//        }
