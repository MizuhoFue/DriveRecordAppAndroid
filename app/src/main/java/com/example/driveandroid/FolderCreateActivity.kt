/*FolderCreateActivity フォルダ作成画面
* folderid　一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* 日付は入力した時点でフォーマットをかけてinsert フォーマットはyyyy/MM/dd ボタンダイアログは画面左上ツールバーで×を押すと破棄してホームに戻るか聞くもの
* タスク：文字数入力チェックを入れる→関数化試み中だが後回し
* ?：
* 更新日2020年11月24日
* 更新者：笛木
* */
package com.example.driveandroid

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.activity_folder_create.*
import java.text.SimpleDateFormat

class FolderCreateActivity : AppCompatActivity() {
    //DB用変数用意　ParagraphInfoテーブル操作
    private val dbName: String = "drivedb"  //DB名
    private val dbVersion: Int = 1  //これがいまいちわからない
    private val tableName1: String = "FolderInfo"    //テーブル名

    //値を格納する変数用意
    //完全一致するものはひとつしかないと思うけれどひとまずfolderid用配列
    private var arrayFolderId: ArrayList<Int> = arrayListOf()
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

        //textWatcherにこれを入れると入力時すぐにフォーマットできる？余裕があったらやる　
        val form = SimpleDateFormat("yyyy/MM/dd")
        //memberNumをtextWatcherで数えて表示する処理をいれる

        //入力完了が押されたらinsert、値受け渡しの必要はないのでホームのフォルダーListに戻る
        createEnd.setOnClickListener {
            //checkData的なメソッドでまとめたい
            //checkDate(putDate,putTitle,putMember1,putMember2,putMember3,putMember4,putMember5,putMember6)
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
            //入力チェック終わり
            //入力、変数に入れた中身を確認
            Log.d(
                "入力した中身", "date:${date} title:${title} member1:${member1} " +
                        "member2:${member2} member3:${member3} member4:${member4} member5:${member5} member6:${member6}"
            )
            //insertメソッド呼び出し
            insertData(date, title, member1, member2, member3, member4, member5, member6)
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
            insertData(date, title, member1, member2, member3, member4, member5, member6)
            //MoneyInsert用SELECT
            //insertできたか確認したらたった今入れたものをセレクトという処理をいれたい if(result)?
            val result =
                selectData(date, title, member1, member2, member3, member4, member5, member6)

            //一個しか入らないと思うから0番目？
            folderid = result[0]
            Log.d("folderidの値確認", "${folderid}")

            val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)
            //idとActivity名をMoneyInsertに送る
            intent.putExtra(EXTRA_FOLDERID, folderid)
            intent.putExtra(EXTRA_ACTIVITYNAME, this::class.java.simpleName)
            startActivity(intent)
            //クリアタスクなし・金額入力画面遷移後はフォルダ作成をフィニッシュ
            finish()
        }

        //仮置きバツボタン（exit）の処理
        exit.setOnClickListener {
            //ダイアログ表示して破棄して戻るかやっぱり登録するのか選ぶ
            //クリアタスクなし・フォルダ作成を閉じるだけ=intent, startActivityいらない
            finish()
        }

    }

    //checkData戻り値用　配列だとどうなる・・・？
    data class checkArray(
        val date: Int,
        val title: String,
        val member1: String,
        val member2: String,
        val member3: String,
        val member4: String,
        val member5: String,
        val member6: String
    )
    //ここでeditableで引数設定
//    fun checkData(
//        putDate: Editable,
//        putTitle: Editable,
//        putMember1: Editable,
//        putMember2: Editable,
//        putMember3: Editable,
//        putMember4: Editable,
//        putMember5: Editable,
//        putMemeber6: Editable  //isNullOrEmpty
//    ):checkArray {
//        //textがコンパイルエラーになる　length?　一旦保留、ParagraphInfo優先
//        if(!putDate.length.) {
//            date = putDate.length.toString().toInt()
//            Log.d("日付の値", "${date}")
//        }
//        val checkArray=checkArray(date,member1,member2,member3,member4,member5,member6)
//        return checkArray
//    }

    //insert用文FolderInfo用　ひとまずDBBrowserの方で確認できた
    fun insertData(
        date: Int, title: String, member1: String,
        member2: String?, member3: String?, member4: String?, member5: String?, member6: String?
    ) {
        try {
            val dbHelper =
                DriveDBHelper(this, dbName, null, dbVersion) //この時点でテーブルが作られてる
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("date", date)
            values.put("title", title)
            values.put("member1", member1)
            values.put("member2", member2)
            values.put("member3", member3)
            values.put("member4", member4)
            values.put("member5", member5)
            values.put("member6", member6)
            //クエリ実行
            val result = database.insertOrThrow(tableName1, null, values)
            //入力した中身を確認
            Log.d(
                "insertした中身", "date:${date} title:${title} member1:${member1} " +
                        "member2:${member2} member3:${member3} member4:${member4} member5:${member5} member6:${member6}"
            )
        } catch (exception: Exception) {
            Log.e("InsertData", exception.toString())
        }
    }

    //select用関数　引数はinsertしたばかりのデータ　戻り値はArray<Int>型
    fun selectData(
        date: Int, title: String, member1: String,
        member2: String?, member3: String?, member4: String?, member5: String?, member6: String?
    ): ArrayList<Int> {
        try {
            val dbHelper = DriveDBHelper(this, dbName, null, dbVersion)
            val database = dbHelper.readableDatabase
            val values = ContentValues()
            //select文　たった今insertした内容と一致するもののfolderidのみ受け取る
            val sql =   //String型の変数はシングルクオテーションで囲むのを忘れずに
                "SELECT * FROM ${tableName1} WHERE date=${date} AND title='${title}'AND member1='${member1}' AND " +
                        "member2='${member2}' AND member3='${member3}' AND member4='${member4}' AND member5='${member5}' AND member6='${member6}'"

            //クエリ実行 cursorで結果セット受け取り？
            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                Log.d("テーブルの登録件数", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayFolderId.add(cursor.getInt(0))//idだけでいい　
                    cursor.moveToNext()
                }
            }
            //folderidを取り出して変数に代入　どうやる

        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
        }
        //idが入った配列を返す
        return arrayFolderId
    }//selectData閉じ
}

