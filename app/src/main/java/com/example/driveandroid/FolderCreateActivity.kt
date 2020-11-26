/*FolderCreateActivity フォルダ作成画面
* folderid　一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* 日付は入力した時点でフォーマットをかけてinsert フォーマットはyyyy/MM/dd ボタンダイアログは画面左上ツールバーで×を押すと破棄してホームに戻るか聞くもの
* タスク：文字数入力チェックを入れる
* ?：端末の戻るボタンの挙動・・・MoneyInsertから戻るボタンでホームに戻るようにしてある
* 更新日2020年11月18日
* 更新者：笛木
* */
package com.example.driveandroid

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
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

            // BuilderからAlertDialogを作成
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.finish_message) // タイトル
                .setPositiveButton(R.string.yes) { dialog, which -> // OK
                    finish()
                }
                .setNegativeButton(R.string.no) { dialog, which -> //no
                    Intent(this@FolderCreateActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        //memberNumをtextwatchで数えて表示する処理をいれる

        //入力完了が押されたらinsert、値受け渡しの必要はないのでホームのフォルダーListに戻る
        createEnd.setOnClickListener {

            //入力チェックしてから
            val dbHelper2 =
                DriveDBHelper(applicationContext, dbName, null, dbVersion)
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
            //リスト遷移後はフォルダ作成を閉じる=startActivityでフォルダ一覧を作成しなくてもよい
            finish()
        }

        //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
        money.setOnClickListener {
            //入力チェック、エラーがある場合はダイアログ表示
            //まずinsertする ひとまず仮をいれておく

            //空チェックして各変数にいれる　ログで確認　関数にしたいがid.textの型がいまいちわからないので引数設定が難しそう
            //日付が空じゃない場合dateに入れる
            if (!putDate.text.isNullOrEmpty()) {
                date = putDate.text.toString().toInt()
                Log.d("日付の値", "${date}")
            }

            if (!putTitle.text.isNullOrEmpty()) {
                title = putTitle.text.toString()
                Log.d("タイトル名", "${title}")
            }

            if (!putMember1.text.isNullOrEmpty()) {
                member1 = putMember1.text.toString()
                Log.d("メンバー1", "${member1}")
            }
            if (!putMember2.text.isNullOrEmpty()) {
                member2 = putMember2.text.toString()
                Log.d("メンバー2", "${member2}")
            }

            if (!putMember3.text.isNullOrEmpty()) {
                member3 = putMember3.text.toString()
                Log.d("メンバー3", "${member3}")
            }

            if (!putMember4.text.isNullOrEmpty()) {
                member4 = putMember4.text.toString()
                Log.d("メンバー4", "${member4}")
            }

            if (!putMember5.text.isNullOrEmpty()) {
                member5 = putMember5.text.toString()
                Log.d("メンバー5", "${member5}")
            }

            if (!putMember6.text.isNullOrEmpty()) {
                member6 = putMember6.text.toString()
                Log.d("メンバー6", "${member6}")
            }
            //入力した中身を確認
            Log.d(
                "入力した中身", "date:${date} title:${title} member1:${member1} " +
                        "member2:${member2} member3:${member3} member4:${member4} member5:${member5} member6:${member6}"
            )
            //insertメソッド呼び出し
//            insertData(date, title, member1, member2, member3, member4, member5, member6)
//            //MoneyInsert用SELECT
//            //insertできたか確認したらたった今入れたものをセレクトという処理をいれたい if(result)?
//            val result =
//                selectData(date, title, member1, member2, member3, member4, member5, member6)
//            Log.d("result", "${result}")
//
//            //一個しか入らないと思うから0番目？
//            folderid = result[0]
//
//            folderid = 2
//
//            Log.d("folderidの値確認", "${folderid}")
//            val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)
//            //idとActivity名をMoneyInsertに送る
//            intent.putExtra(EXTRA_FOLDERID, folderid)
//            intent.putExtra(EXTRA_ACTIVITYNAME, this::class.java.simpleName)
//            startActivity(intent)
//            //クリアタスクなし・金額入力画面遷移後はフォルダ作成をフィニッシュ
//            finish()
//        }
//
//        //insert用文FolderInfo用　ひとまずDBBrowserの方で確認できた
//        fun insertData(
//            date: Int, title: String, member1: String,
//            member2: String?, member3: String?, member4: String?, member5: String?, member6: String?
//        ) {
//            try {
//                val dbHelper =
//                    DriveDBHelper(this, DB_NAME, null, DB_VERSION) //この時点でテーブルが作られてる
//                val database = dbHelper.writableDatabase
//                val values = ContentValues()
//                values.put("date", date)
//                values.put("title", title)
//                values.put("member1", member1)
//                values.put("member2", member2)
//                values.put("member3", member3)
//                values.put("member4", member4)
//                values.put("member5", member5)
//                values.put("member6", member6)
//                //クエリ実行
//                val result = database.insertOrThrow(FOLDER_INFO, null, values)
//                //入力した中身を確認
//                Log.d(
//                    "insertした中身", "date:${date} title:${title} member1:${member1} " +
//                            "member2:${member2} member3:${member3} member4:${member4} member5:${member5} member6:${member6}"
//                )
//            } catch (exception: Exception) {
//                Log.e("InsertData", exception.toString())
//            }
//        }
//
//        //select用関数　引数はinsertしたばかりのデータ　戻り値はArray<Int>型
//        fun selectData(
//            date: Int, title: String, member1: String,
//            member2: String?, member3: String?, member4: String?, member5: String?, member6: String?
//        ): ArrayList<Int> {
//            try {
//                val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
//                val database = dbHelper.readableDatabase
//
//                //select文　たった今insertした内容と一致するもののfolderidのみ受け取る
//                val sql =   //String型の変数はシングルクオテーションで囲むのを忘れずに
//                    "SELECT * FROM ${FOLDER_INFO} WHERE date=${date} AND title='${title}'AND member1='${member1}' AND " +
//                            "member2='${member2}' AND member3='${member3}' AND member4='${member4}' AND member5='${member5}' AND member6='${member6}'"
//                Log.d("SQL実行", sql)
//                //クエリ実行 cursorで結果セット受け取り？
//                val cursor = database.rawQuery(sql, null)
//                if (cursor.count > 0) {
//                    Log.d("テーブルの登録件数", "${cursor.count}")
//                    cursor.moveToFirst()
//                    while (!cursor.isAfterLast) {
//                        arrayFolderId.add(cursor.getInt(0))//idだけでいい　
//                        cursor.moveToNext()
//                    }
//                }
//                //folderidを取り出して変数に代入　どうやる
//
//            } catch (exception: Exception) {
//                Log.e("SelectData", exception.toString())
//            }
//            //idが入った配列を返す
//            return arrayFolderId
        }//selectData閉じ
    }

}


