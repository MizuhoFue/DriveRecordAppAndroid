package com.example.driveandroid
/*FolderCreateActivity フォルダ作成画面
* folderid　一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* 日付は入力した時点でフォーマットをかけてinsert フォーマットはyyyy/MM/dd ボタンダイアログは画面左上ツールバーで×を押すと破棄してホームに戻るか聞くもの
* タスク：文字数入力チェックを入れる
* */
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.activity_folder_create.*

class FolderCreateActivity : AppCompatActivity() {

    //DB用変数用意　ParagraphInfoテーブル操作
    private val dbName: String = "drivedb"  //DB名
    private val dbVersion: Int = 1  //これがいまいちわからない
    private val tableName1: String = "FolderInfo"    //テーブル名

    //値を格納する変数用意
    private var folderid = 0 //セレクトした後に入れる
    private var title = "" //タイトル
    private var date = 0 //日付　
    private var member1 = ""
    private var member2 = ""
    private var member3 = ""
    private var member4 = ""
    private var member5 = ""
    private var member6 = ""
    private var memberNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_create)

        setting.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, SupportActivity::class.java)
            startActivity(intent)
        }

        //タイトルラベルの左側のナビゲーションアイテムの設置
        drive_toolbar.setNavigationIcon(android.R.drawable.ic_delete)
        //ナビゲーションアイテムのリスナー
        drive_toolbar.setNavigationOnClickListener {
//            val intent = Intent(this@FolderCreateActivity, FolderListActivity::class.java)
//            startActivity(intent)

            // BuilderからAlertDialogを作成
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.finish_message) // タイトル
                .setPositiveButton(R.string.yes) { dialog, which -> // OK
                    //moveTaskToBack(true)
                    val intent = Intent(this@FolderCreateActivity, FolderListActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton(R.string.no) { dialog, which -> //no
                    Intent(this@FolderCreateActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        //memberNumをtextwatchで数えて表示する処理をいれる

        //入力完了が押されたらinsert、値受け渡しの必要はないのでListに戻る
        createEnd.setOnClickListener {

            //入力チェックしてから
            val dbHelper2 =
                FolderInfoDBHelper(applicationContext, dbName, null, dbVersion)
            val database = dbHelper2.writableDatabase
            val values = ContentValues()
            values.put("title", title)
            values.put("date", date) //フォーマットした状態で入れたい
            values.put("member1", member1)
            values.put("member2", member2)
            values.put("member3", member3)
            values.put("member4", member4)
            values.put("member5", member5)
            values.put("member6", member6)
            //val result = database.insertOrThrow(tableName1, null, values)

            val intent = Intent(this@FolderCreateActivity, FolderListActivity::class.java)
            startActivity(intent)
        }
        money.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)

            //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
            money.setOnClickListener {
                //入力チェック、エラーがある場合はダイアログ表示
                //まずinsertする ひとまず仮をいれておく
                folderid = 2
                Log.d("folderidの値確認", "${folderid}")
                val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)
                intent.putExtra(EXTRA_FOLDERID, folderid)
                startActivity(intent)
            }
        }
    }
}