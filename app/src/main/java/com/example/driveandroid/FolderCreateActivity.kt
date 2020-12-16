/*FolderCreateActivity フォルダ作成画面
* 一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* 前回からの変更点： MoneyInsertヘいく際のselectメソッドは全体セレクトして最新の行のfolderidのみ取る仕様に変更
* タスク：（Listの完成後）文字数入力チェックを入れる、insert周りをデータクラス使って整理
* 更新日：2020年12月15日
* 更新者：笛木
* */
package com.example.driveandroid

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
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
    //Constantsの値を使っているのでDB用変数削除
    //セレクトメソッド戻り値はメソッド内で初期化
    //insertの引数用 クラスは頭文字大文字
    data class InsertArray(
        val date: String,
        val title: String,
        val member1: String,
        val member2: String?,
        val member3: String?,
        val member4: String?,
        val member5: String?,
        val member6: String?
    )

    //データクラスはセットする時に初期化
    //DB用変数はその場でisNullOrBlankチェックした時に初期化、代入
    private var memberNum = 0 //textWatcher用　後ほど使う

    //DatePicker用変数初期化
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
        //日付入力DatePicker
        datePick.setOnClickListener {
            //現在の年月日を求めて初期値とする
            val onlyDate = LocalDate.now()
            Log.d("今の年月日", "$onlyDate")
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth ->
                    datePick.text = "$year/${month + 1}/$dayOfMonth" //ボタンのところに表示
                    dateYear = year
                    dateMonth = month + 1
                    dateDayOfMonth = dayOfMonth
                    Log.d("選択した年月日", "$dateYear,$dateMonth,$dateDayOfMonth")
                }, onlyDate.year, onlyDate.monthValue - 1, onlyDate.dayOfMonth //初期値セット
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

        //TODO memberNumをtextWatcherで数えて表示する処理をいれる
        //入力完了が押されたらinsert、値受け渡しの必要はないのでホームのフォルダーListに戻る
        createEnd.setOnClickListener {
            //エラーはif文にelseをつけてメッセージ設定?
            //空の場合エラーメッセージを出すdate、titleとmember1 TODO エラーメッセージ用意
            //DatePickerで入力されたものをDB登録用変数に入れる
            val date =
                if (dateYear != 0 && dateMonth != 0 && dateDayOfMonth != 0) { //ちゃんと日付選択されているならば
                    //変数に格納、一桁の場合は0埋めを行う
                    val month = if ("$dateMonth".length == 1) "0$dateMonth" else "$dateMonth"
                    val day =
                        if ("$dateDayOfMonth".length == 1) "0$dateDayOfMonth" else "$dateDayOfMonth"
                    //この時点でスラッシュ入りにしてString型としてdateに格納
                    "$dateYear/$month/$day"
                } else {
                    "" //日付選択されていない場合空白代入
                }

//            if (!date.isNullOrEmpty()) {
                Log.d("選択日付スラッシュ入り", date)

                val title =
                    if (!putTitle.text.isNullOrBlank()) putTitle.text.toString() else "" //nullがダメなので空白？
//                if (!title.isNullOrEmpty()) {
                    Log.d("タイトル名", title)

                    val member1 =
                        if (!putMember1.text.isNullOrBlank()) putMember1.text.toString() else ""//nullがダメなので空白？
//                    if (!member1.isNullOrEmpty()) {
                        Log.d("メンバー1", member1)

                        //入力されていればmember変数に入力値格納、されていなければnull代入
                        val member2 =
                            if (!putMember2.text.isNullOrBlank()) putMember2.text.toString() else null
                        Log.d("メンバー2", "$member2")

                        val member3 =
                            if (!putMember3.text.isNullOrBlank()) putMember3.text.toString() else null
                        Log.d("メンバー3", "$member3")

                        val member4 =
                            if (!putMember4.text.isNullOrBlank()) putMember4.text.toString() else null
                        Log.d("メンバー4", "$member4")

                        val member5 =
                            if (!putMember5.text.isNullOrBlank()) putMember5.text.toString() else null
                        Log.d("メンバー5", "$member5")

                        val member6 =
                            if (!putMember6.text.isNullOrBlank()) putMember6.text.toString() else null
                        Log.d("メンバー6", "$member6")

                        //入力チェック終わり TODO このタイミングでエラーメッセージの中身を確認　あったらエラーダイアログ表示

                        //入力、変数に入れた中身を確認
                        Log.d(
                            "入力した中身", "date:$date title:$title member1:$member1" +
                                    "member2:$member2 member3:$member3 member4:$member4 member5:$member5 member6:$member6"
                        )
                        //データクラスを使ってinsertInfoにまとめる
                        val insertInfo =
                            InsertArray(
                                date,
                                title,
                                member1,
                                member2,
                                member3,
                                member4,
                                member5,
                                member6
                            )
                        Log.d("insertInfoの中身", "$insertInfo")
                        //insertメソッド呼び出し
                        insertData(insertInfo)
                        //TODO ここまでをメソッド化予定
                        //リスト遷移後はフォルダ作成を閉じる=startActivityでフォルダ一覧を作成しなくてもよい
                        finish()
//                    } else {
//                        AlertDialog.Builder(this)
//                            .setMessage("メンバーを入力してください")
//                            .setPositiveButton(
//                                "OK",
//                                DialogInterface.OnClickListener { _, _ ->
//                                    //ignore
//                                })
//                            .show()
//                    }
//                } else {
//                    AlertDialog.Builder(this)
//                        .setMessage("タイトルを入力してください")
//                        .setPositiveButton(
//                            "OK",
//                            DialogInterface.OnClickListener { _, _ ->
//                                //ignore
//                            })
//                        .show()
//                }
//            } else {
//                AlertDialog.Builder(this)
//                    .setMessage("日付を入力してください")
//                    .setPositiveButton(
//                        "OK",
//                        DialogInterface.OnClickListener { _, _ ->
//                            //ignore
//                        })
//                    .show()
//            }
        }

        //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
        money.setOnClickListener {
            //入力チェック エラーはelseでメッセージ設定 上記inputCompとエラーチェック、insertまでの流れは同じ
            val date =
                if (dateYear != 0 && dateMonth != 0 && dateDayOfMonth != 0) {//ちゃんと日付選択されているならば
                    //変数に格納、一桁の場合は0埋めを行う
                    val month = if ("$dateMonth".length == 1) "0$dateMonth" else "$dateMonth"
                    val day =
                        if ("$dateDayOfMonth".length == 1) "0$dateDayOfMonth" else "$dateDayOfMonth"
                    "$dateYear/$month/$day" //この時点でスラッシュ入りにしてString型としてdateに格納
                } else {
                    ""
                }
            //TODO ここでもう一度チェックして空だった場合はエラ〜メッセージ isEmptyなどで
            Log.d("日付スラッシュ入り", date)

            val title =
                if (!putTitle.text.isNullOrBlank()) putTitle.text.toString() else "" //nullがダメなので空白？
            Log.d("タイトル名", title)

            val member1 =
                if (!putMember1.text.isNullOrBlank()) putMember1.text.toString() else "" //nullがダメなので空白？
            Log.d("メンバー1", member1)

            //入力されていればmember変数に入力値格納、されていなければnull代入
            val member2 = if (!putMember2.text.isNullOrBlank()) putMember2.text.toString() else null
            Log.d("メンバー2", "$member2")

            val member3 = if (!putMember3.text.isNullOrBlank()) putMember3.text.toString() else null
            Log.d("メンバー3", "$member3")

            val member4 = if (!putMember4.text.isNullOrBlank()) putMember4.text.toString() else null
            Log.d("メンバー4", "$member4")

            val member5 = if (!putMember5.text.isNullOrBlank()) putMember5.text.toString() else null
            Log.d("メンバー5", "$member5")

            val member6 = if (!putMember6.text.isNullOrBlank()) putMember6.text.toString() else null
            Log.d("メンバー6", "$member6")

            //TODO このタイミングでエラーメッセージをチェック　中身があったらエラーダイアログ表示
            //入力した中身を確認
            Log.d(
                "入力した中身", "date:$date title:$title member1:$member1" +
                        "member2:$member2 member3:$member3 member4:$member4 member5:$member5 member6:$member6"
            )
            //データクラスを使ってinsertInfoにまとめる
            val insertInfo =
                InsertArray(date, title, member1, member2, member3, member4, member5, member6)
            Log.d("insertInfoの中身", "$insertInfo")
            //insertメソッド呼び出し
            insertData(insertInfo)
            //TODO　ここまでをメソッド化予定
            //MoneyInsert用SELECT
            //insertできたか確認したらたった今入れたものをセレクト if(result)?
            val result =
                selectFolder() //最新の一行を取得するselectFolderメソッドに変更
            Log.d("result", "$result")

            //一個しか入らないので0番目
            val folderId = result?.get(0)
            Log.d("folderIdの値確認", "$folderId")

            val intent = Intent(this@FolderCreateActivity, MoneyInsertActivity::class.java)
            //idとActivity名をMoneyInsertに送る
            intent.putExtra(EXTRA_FOLDERID, folderId)
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
    private fun insertData(
        insertInfo: InsertArray
    ) {
        try {
            val dbHelper =
                DriveDBHelper(this, DB_NAME, null, DB_VERSION)
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
                "insertできた中身",
                "date:${insertInfo.date} title:${insertInfo.title} member1:${insertInfo.member1} " +
                        "member2:${insertInfo.member2} member3:${insertInfo.member3} member4:${insertInfo.member4} member5:${insertInfo.member5} member6:${insertInfo.member6}"
            )
        } catch (exception: Exception) {
            Log.e("InsertData", exception.toString())
        }
    }

    //selectDataは削除
    /**
     * @return arrayFolderId:ArrayList<Int>? folderidが入った配列
     * */
    private fun selectFolder(): ArrayList<Int>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            //最後のレコードのみを取るSQL文をつくる
            val sql = "SELECT * FROM $FOLDER_INFO ORDER BY folderid DESC LIMIT 1 "
            Log.d("SQL文確認", sql)
            val cursor = database.rawQuery(sql, null)
            val arrayFolderId = arrayListOf<Int>()
            if (cursor.count > 0) {
                Log.d("テーブルの登録件数", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayFolderId.add(cursor.getInt(0))//idだけでいい　
                    cursor.moveToNext()
                }
            }
            cursor.close() //クローズ
            return arrayFolderId
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }
}