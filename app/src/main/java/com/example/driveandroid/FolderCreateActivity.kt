/*FolderCreateActivity フォルダ作成画面
* folderid　一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* タスク：文字数入力チェックを入れる DatePickerを入れる→数字のままでひとまず登録はできる状態 ListでStringにしてからsubStringでどうにかする？
* insert周りをデータクラス使って整理
* 更新日2020年12月2日
* 更新者：笛木
* */
package com.example.driveandroid

import android.app.DatePickerDialog
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
import java.time.LocalDate

class FolderCreateActivity : AppCompatActivity() {

    //DB用変数用意　ParagraphInfoテーブル操作
    private val dbName: String = "drivedb"  //DB名
    private val dbVersion: Int = 1  //これがいまいちわからない
    private val tableName1: String = "FolderInfo"    //テーブル名
    //セレクトメソッド戻り値用
    private var arrayFolderId: ArrayList<Int> = arrayListOf()
    //insert、selectの引数用
    data class insertArray(
        val date: Int,
        val title: String,
        val member1: String,
        val member2: String?,
        val member3: String?,
        val member4: String?,
        val member5: String?,
        val member6: String?
    )
    //データクラスを使ってinsertInfoにまとめる　初期化？をしている
    private var insertInfo =
        insertArray(0, "", "", null, null, null, null, null)

    //入力した値を格納する変数用意 最終的にデータクラスにまとめる
    private var folderid = 0 //セレクトした後に入れる
    private var title = "" //タイトル
    private var date = 0 //日付　
    private var member1 = ""
    private var member2 = ""
    private var member3 = ""
    private var member4 = ""
    private var member5 = ""
    private var member6 = ""
    private var memberNum = 0 //textwatcher用

    //DatePicker用変数仮
    private var dateYear = 0
    private var dateMonth = 0
    private var dateDayOfMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_create)

        setting.setOnClickListener {
            val intent = Intent(this@FolderCreateActivity, SupportActivity::class.java)
            startActivity(intent)
        }

        //タップした時の年月日を表示
        datePick.setOnClickListener {
            //現在の年月日を求めて初期値とする yearNow、monthNow、dateNow
            val onlyDate = LocalDate.now()
            Log.d("今の年月日", "${onlyDate}")
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth ->
                    datePick.text = "${year}/${month + 1}/${dayOfMonth}" //ボタンのところに表示
                    dateYear = year
                    dateMonth = month + 1
                    dateDayOfMonth = dayOfMonth
                    Log.d("選択した年月日", "${dateYear},${dateMonth},${dateDayOfMonth}")
                }, onlyDate.year, onlyDate.monthValue - 1, onlyDate.dayOfMonth //
            )
            datePickerDialog.show()
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

        //TODO memberNumをtextwatchで数えて表示する処理をいれる
        //入力完了が押されたらinsert、値受け渡しの必要はないのでホームのフォルダーListに戻る
        createEnd.setOnClickListener {

            //checkData的なメソッドでまとめたい
            //checkDate(putDate,putTitle,putMember1,putMember2,putMember3,putMember4,putMember5,putMember6)
            //DatePickerで入力されたものをDB登録用変数に入れる 文字列で足してInt型にキャストすればデータベースに影響はなし folderListのこれ！にそれぞれ入れたい・・・
            if (dateYear != 0 && dateMonth != 0 && dateDayOfMonth != 0) {//ちゃんと日付選択されているならば
                val strDate = "${dateYear}" + "${dateMonth}" + "${dateDayOfMonth}"
                date = Integer.parseInt(strDate)
                Log.d("数字にできたか確認", "${insertInfo.date}")
            }

            if (!putTitle.text.isNullOrEmpty()) {
                title = putTitle.text.toString()
                Log.d("タイトル名", "${insertInfo.title}")
            }

            if (!putMember1.text.isNullOrEmpty()) {
                member1 = putMember1.text.toString()
                Log.d("メンバー1", "${insertInfo.member1}")
            }
            if (!putMember2.text.isNullOrEmpty()) {
                member2 = putMember2.text.toString()
                Log.d("メンバー2", "${insertInfo.member2}")
            }

            if (!putMember3.text.isNullOrEmpty()) {
                member3 = putMember3.text.toString()
                Log.d("メンバー3", "${insertInfo.member3}")
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
            //データクラスを使ってinsertInfoにまとめる
            insertInfo =
                insertArray(date, title, member1, member2, member3, member4, member5, member6)
            Log.d("insertInfoの中身", "${insertInfo}")
            //insertメソッド呼び出し
            insertData(insertInfo)
            //リスト遷移後はフォルダ作成を閉じる=startActivityでフォルダ一覧を作成しなくてもよい
            finish()
        }

        //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
        money.setOnClickListener {
            //入力チェック、エラーがある場合はダイアログ表示

            //DatePickerで入力されたものをDB登録用変数に入れる 文字列で足してInt型にキャストすればデータベースに影響はなし
            var strDate = "${dateYear}" + "${dateMonth}" + "${dateDayOfMonth}"
            date = Integer.parseInt(strDate)
            Log.d("数字にできたか確認", "${date}")

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
            //データクラスを使ってinsertInfoにまとめる
            insertInfo =
                insertArray(date, title, member1, member2, member3, member4, member5, member6)
            Log.d("insertInfoの中身", "${insertInfo}")
            //insertメソッド呼び出し
            insertData(insertInfo)
            //MoneyInsert用SELECT
            //insertできたか確認したらたった今入れたものをセレクトという処理をいれたい if(result)?
            val result =
                selectData(insertInfo)
            Log.d("result", "${result}")

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
    }

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

    /**insertData FolderInfo insert用メソッド
     * @param insertInfo 入力項目
     */
    fun insertData(
        insertInfo: insertArray
    ) {
        try {
            val dbHelper =
                DriveDBHelper(this, DB_NAME, null, DB_VERSION) //この時点でテーブルが作られてる
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("date", insertInfo.date)
            values.put("title", insertInfo.title)
            values.put("member1", insertInfo.member1)
            values.put("member2", insertInfo.member2)
            values.put("member3", insertInfo.member3)
            values.put("member4", insertInfo.member4)
            values.put("member5", insertInfo.member5)
            values.put("member6", insertInfo.member6)
            //クエリ実行
            val result = database.insertOrThrow(FOLDER_INFO, null, values)
            //入力した中身を確認
            Log.d(
                "insertした中身",
                "date:${insertInfo.date} title:${insertInfo.title} member1:${insertInfo.member1} " +
                        "member2:${insertInfo.member2} member3:${insertInfo.member3} member4:${insertInfo.member4} member5:${insertInfo.member5} member6:${insertInfo.member6}"
            )
        } catch (exception: Exception) {
            Log.e("InsertData", exception.toString())
        }
    }

    /**selectData FolderInfo idをselect用メソッド
     * @param insertInfo insertしたばかりの入力項目
     * */
    fun selectData(
        insertInfo: insertArray
    ): ArrayList<Int> {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            //select文　たった今insertした内容と一致するもののfolderidのみ受け取る
            val sql =   //String型の変数はシングルクオテーションで囲むのを忘れずに
                "SELECT * FROM ${FOLDER_INFO} WHERE date=${insertInfo.date} AND title='${insertInfo.title}'AND member1='${insertInfo.member1}' AND " +
                        "member2='${insertInfo.member2}' AND member3='${insertInfo.member3}' AND member4='${insertInfo.member4}' AND member5='${insertInfo.member5}' AND member6='${insertInfo.member6}'"
            Log.d("SQL実行", sql)
            //クエリ実行 cursorで結果セット受け取り？
            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                Log.d("テーブルの登録件数", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayFolderId.add(cursor.getInt(0))//idだけでいい　
                    cursor.moveToNext()
                }
                cursor.close() //クローズ
            }
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
        }
        //idが入った配列を返す
        return arrayFolderId
    }//selectData閉じ
}
