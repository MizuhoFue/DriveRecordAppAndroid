/*FolderCreateActivity フォルダ作成画面
* 一列登録した情報　全一致のものをセレクトして配列に入れた上でidだけMoneyInsertActivityに送る
* 前回からの変更点：人数カウントはtextWatcher、文字数チェックはフォーカスが映った時、完了系ボタンが押された時の二回行う
* 背景タップでフォーカスを変える処理追加
* 更新日：2020年12月23日
* 更新者：笛木
* */
package com.example.driveandroid

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
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

        watchMember.text = "0" //最初から表示しておく用

        //editTextの配列
        val editArray =
            listOf(putTitle, putMember1, putMember2, putMember3, putMember4, putMember5, putMember6)
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
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
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

        putTitle.apply {
            //putTitleを数える 文字数オーバーはエラーダイアログ
            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }

        }
        //人数カウント、memberNum反映
        putMember1.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        putMember2.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        putMember3.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        putMember4.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        putMember5.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        putMember6.apply {
            doAfterTextChanged { editable ->
                memberNum = memberCount()
                watchMember.text = memberNum.toString()
            }

            setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    //フォーカスが外れた時に文字数チェック
                    (v as? EditText)?.also { editText ->
                        textCheck(editText)
                    }
                }
            }
        }

        //member文字数チェック&カウント終了
        //入力完了が押されたらinsert、値受け渡しの必要はないのでホームのフォルダーListに戻る
        createEnd.setOnClickListener {
            //textCheck　10文字再チェック
            editArray.forEach {
                if (!textCheck(it)) {
                    return@setOnClickListener //
                }
            }
            //各入力項目をデータクラスに格納
            val insertInfo = inputCheck()
            //return errCheckでerrMsg
            //空白チェック　エラーがある場合は戻り値が""ではない
            val errMsg = errorCheck(insertInfo)

            //エラー確認
            if (errMsg != "") {
                val dialog = AlertDialog.Builder(this)
                    .setMessage(errMsg)
                    .setPositiveButton("OK") { _, _ ->
                        //ignore
                    }
                    .create()
                dialog.show()
            } else {//エラーなし、ダイアログ表示もない場合はinsert
                //入力、変数に入れた中身を確認
                //データクラスを使ってinsertInfoにまとめる
                Log.d("insertするinsertInfoの中身", "$insertInfo")
                //insertメソッド呼び出し
                insertData(insertInfo)
                //TODO ここまでをメソッド化予定 どこまでまとめる？
                //リスト遷移後はフォルダ作成を閉じる=startActivityでフォルダ一覧を作成しなくてもよい
                finish()
            }
        }

        //金額入力が押されたら入力チェック、insert、selectしたものを配列に入れる、
        money.setOnClickListener {
            //textCheck　10文字再チェック
            editArray.forEach {
                if (!textCheck(it)) {
                    return@setOnClickListener
                }
            }
            //各入力項目をデータクラスに格納
            val insertInfo = inputCheck()
            //空白チェック　エラーがある場合は戻り値が""ではない
            val errMsg = errorCheck(insertInfo)

            //入力チェック終わり エラー確認
            if (errMsg != "") {
                val dialog = AlertDialog.Builder(this)
                    .setMessage(errMsg)
                    .setPositiveButton("OK") { _, _ ->
                        //ignore
                    }
                    .create()
                dialog.show()
            } else { //エラーなし、エラーダイアログ表示がない場合は
                //insertメソッド呼び出し
                insertData(insertInfo)

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
    }

    /**
     * dateのnullチェック、その他の値のnullチェックをするcheckDataを呼び出す
     *@return  入力する全ての値をInsertArray型ArrayListにまとめる
     *
     * */
    private fun inputCheck(): InsertArray {
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

        //nullチェック dateだけStringか空白確定の状態で引数に入れる
        return checkData(
            date,
            putTitle,
            putMember1,
            putMember2,
            putMember3,
            putMember4,
            putMember5,
            putMember6
        )
    }

    /**　文字数チェック
     *10文字以上の入力があった場合ダイアログを出す
     * */
    private fun textCheck(editText: EditText): Boolean {
        return if (editText.text.length > 10) { //viewクラスをeditTextクラスにキャスト、文字数チェック
            val dialog = AlertDialog.Builder(this@FolderCreateActivity)
                .setMessage("10文字以内で入力してください。")
                .setPositiveButton("OK") { _, _ ->
                    //OK押したら中身削除
                    editText.text.clear()
                }
                .create()
            dialog.show()
            false
        } else {
            true
        }
    }

    /** メンバーカウント用
     * @return メンバーを入れた配列でnull、""空白を除いた要素の数=メンバーの人数
     * */
    private fun memberCount(): Int {
        val inputMemberList = listOfNotNull(
            putMember1.text,
            putMember2.text,
            putMember3.text,
            putMember4.text,
            putMember5.text,
            putMember6.text
        )
        return inputMemberList.filterNot { it.toString() == "" }.size
    }

    /** 各EditTextをnullチェックし変数に入れる、　insertArray型変数にセット
     * inputCheckの中で呼び出し
     * @return  checkArray<InsertArray>
     * */
    private fun checkData(
        date: String,
        putTitle: EditText,
        putMember1: EditText,
        putMember2: EditText,
        putMember3: EditText,
        putMember4: EditText,
        putMember5: EditText,
        putMember6: EditText  //isNullOrEmpty
    ): InsertArray {
        //date以外の値を各変数に代入
        val title =
            if (!putTitle.text.isNullOrBlank()) putTitle.text.toString() else "" //nullがダメなので空白？
        Log.d("タイトル名", title)

        val member1 =
            if (!putMember1.text.isNullOrBlank()) putMember1.text.toString() else "" //nullがダメなので空白？
        Log.d("メンバー1", member1)

        //入力されていればmember変数に入力値格納、されていなければnull代入
        val member2 = if (!putMember2.text.isNullOrBlank()) putMember2.text.toString() else null
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

        return InsertArray( //return
            date,
            title,
            member1,
            member2,
            member3,
            member4,
            member5,
            member6
        )
    }

    /**insertする前の空白エラーチェック
     *@return errMsg:String エラーメッセージor空白
     * */
    private fun errorCheck(insertInfo: InsertArray): String {

        return if (insertInfo.date.isEmpty()) {
            "日付を入力してください"
        } else if (insertInfo.title.isEmpty()) {
            "タイトルを入力してください"
        } else if (insertInfo.member1.isEmpty()) {
            //member1が空文字の状態でmember2から6に値が入っていた場合は
            if (!insertInfo.member2.isNullOrEmpty() || !insertInfo.member3.isNullOrEmpty() || !insertInfo.member4.isNullOrEmpty() || !insertInfo.member5.isNullOrEmpty() || !insertInfo.member6.isNullOrEmpty()) {
                "上からメンバー名を入力してください"
            } else {
                "メンバー名を入力してください"
            }
        } else if (insertInfo.member2 == null) {
            if (!insertInfo.member3.isNullOrEmpty() || !insertInfo.member4.isNullOrEmpty() || !insertInfo.member5.isNullOrEmpty() || !insertInfo.member6.isNullOrEmpty()) {
                "上からメンバー名を入力してください"
            } else {
                ""
            }
        } else if (insertInfo.member3 == null) {
            if (!insertInfo.member4.isNullOrEmpty() || !insertInfo.member5.isNullOrEmpty() || !insertInfo.member6.isNullOrEmpty()) {
                "上からメンバー名を入力してください"
            } else {
                ""
            }
        } else if (insertInfo.member4 == null) {
            if (!insertInfo.member5.isNullOrEmpty() || !insertInfo.member6.isNullOrEmpty()) {
                "上からメンバー名を入力してください"
            } else {
                ""
            }
        } else if (insertInfo.member5 == null) {
            if (!insertInfo.member6.isNullOrEmpty()) {
                "上からメンバー名を入力してください"
            } else {
                ""
            }
        } else {
            ""
        }
    }

    /**insertData FolderInfoにinsert用メソッド
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
    /** FolderInfoセレクト
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

    //背景タップでeditTextのフォーカスが外れるようにする
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        FolderCreate.requestFocus()
        return super.dispatchTouchEvent(ev)
    }
}
