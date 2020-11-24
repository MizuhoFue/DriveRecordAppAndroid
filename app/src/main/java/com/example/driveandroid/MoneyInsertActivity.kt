/*FolderCreate、FolderDetailから遷移
*画面名：MoneyInsertActivity 金額入力画面　使用した金額や使用用途の入力・登録を行う　
*整理：入力された値を変数に入れてSQL実行　一度に登録できるのは1項目
*遷移先：FolderList,FolderDetail ダイアログで選択、遷移する
*ボタンメモ：入力完了：id:inputComp　
* やること:ダイアログ遷移を入れる→入力完了を押して遷移先決めたらinsert呼び出し、登録して遷移、データベース接続、　とりあえず選択された値を変数に入れられるようにするのが先？
* Numberの値変数に入れるのはすぐできそうな感じ ダイアログに「キャンセル」追加
* 更新者：笛木
* 更新日：2020年11月21日
* */
package com.example.driveandroid

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
import com.example.driveandroid.Constants.Companion.PARAGRAPH_INFO
import kotlinx.android.synthetic.main.activity_folder_create.*
import kotlinx.android.synthetic.main.activity_folder_create.drive_toolbar
import kotlinx.android.synthetic.main.activity_money_insert.*
import kotlinx.android.synthetic.main.activity_folder_create.setting as setting1
//↑グレーアウトしている folderCreateのツールバーをインポートしているせい？

class MoneyInsertActivity : AppCompatActivity() {

    private var payer = ""  //負担者名、項目ごとに異なる可能性あり
    private var paraName = ""   //項目名　登録分によって複数あり
    private var paraCost = 0    //項目の金額　登録分によって複数あり

    // 負担者スピナーの配列　アダプター使用　
    val payerList = arrayListOf<String>()

    //カメラ準備 static役割
//    companion object {
//        const val CAMERA_REQUEST_CODE = 1
//        const val CAMERA_PERMISSION_REQUEST_CODE = 2
//    }
// private var <> Array<payerSpinner>.onItemSelectedListener: AdapterView.OnItemSelectedListener
//    get() {}
//    set() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_insert)

//        //この時点でdbHelper呼び出し？（うまくいかない/戻り値がない）
//        val dbHelper = ParagraphInfoDBHelper(applicationContext, dbName, null, dbVersion)
//        Log.d("dbHelperの中身", "${dbHelper}")
//        if (dbHelper = true) {

//        }
        //FolderDetail、FolderCreateから渡されたfolderidを変数に入れる
        val intent = getIntent()
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: 0 //valでいいのか？ 0の場合はMoneyInsert自体できないようにするか
        //FolderDetail、FolderCreateのどちらから遷移したかのfromActivityを変数に入れる
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？

        Log.d("受け渡されたfolderid", "${folderid}")
        Log.d("どこから遷移", "{$fromActivity}")

        //このタイミングでidを元にselect、selectメソッド内で空チェックを行う予定
        val payers = selectData(folderid)

        //項目スピナー設定 ダイアログ表示、選択項目Spinnerスペースへの表示
        paragraphSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //選択された項目名を変数paraNameに代入
                var spinner1 = parent as? Spinner
                paraName = spinner1?.selectedItem as? String ?: "" //nullだった場合””を入れる
                Log.d("paraNameの値", "${paraName}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ignore
            }
        }

        //アダプターに負担者配列リストを設定
        val Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payers)
        //Spinnerにアダプター設定
        payerSpinner.adapter = Adapter
        //プルダウンをクリックした時ダイアログを表示
        payerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //選択されたアイテムを負担者payerに代入
                var spinner2 = parent as? Spinner
                payer = spinner2?.selectedItem as? String ?: "" //nullだった場合""を入れる
                Log.d("payerの値", "${payer}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ignore
            }
        }

        //入力完了を押した際にダイアログを表示
        inputComp.setOnClickListener {//押したら
            Log.d("inputClick", "${paragraphSpinner}")

            //id:costで入力された数値を文字にして数値にする nullと空文字、スペースが入っているとtrue
            if (!cost.text.isNullOrEmpty()) {
                paraCost = cost.text.toString().toInt()
                Log.d("項目の金額", "${paraCost}")

                //ダイアログのメッセージ、各ボタンの処理を設定　「詳細」/「ホーム」遷移、入力修正のための「キャンセル」　
                AlertDialog.Builder(this)
                    .setMessage("どちらに移動しますか？")
                    .setPositiveButton(
                        "詳細",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                            //insert処理を入れる　うまくいかない↓
                            insertPara(folderid, paraName, paraCost, payer)
                            //FolderCreate→Moneyの場合：startしてfinish()　　
                            // FolderDetail→MoneyInsertの場合：finish()のみ
                            //Createからの遷移の場合
                            if (fromActivity.equals(FolderCreateActivity::class.java.simpleName)) {
                                val intent = Intent(
                                    this@MoneyInsertActivity,
                                    FolderDetailActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                            } else {    //Detailから遷移の場合
                                finish()
                            }
                        })
                    .setNegativeButton(
                        "ホーム",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                            //insert処理を入れたい　うまくいかない↓
                            insertPara(folderid, paraName, paraCost, payer)
                            val intent =
                                Intent(this@MoneyInsertActivity, FolderListActivity::class.java)
                            //クリアタスクして遷移
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        })
                    .setNeutralButton("キャンセル", DialogInterface.OnClickListener { _, _ ->
                        //ignore
                    })
                    .show() //またはcreate() ?
            }
        }
        //else { エラーダイアログ、エラーを確認して入力内容破棄、再入力させる 後回し
        //
        //            }

        //カメラボタンをクリックするとCameraActivityに遷移
        camera.setOnClickListener {
            val intentCamera = Intent(this@MoneyInsertActivity, CameraActivity::class.java)
            startActivity(intentCamera)
            //フィニッシュせずに遷移
        }

        //タイトルラベルの左側のナビゲーションアイテムの設置
        drive_toolbar.setNavigationIcon(android.R.drawable.ic_delete)
        //ナビゲーションアイテムのリスナー
        drive_toolbar.setNavigationOnClickListener {

            // BuilderからAlertDialogを作成
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.finish_message) // タイトル
                .setPositiveButton(R.string.yes) { dialog, which -> // OK
                    //moveTaskToBack(true)
                  finish()
                }
                .setNegativeButton(R.string.no) { dialog, which -> //no
                    Intent(this@MoneyInsertActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        setting.setOnClickListener {
            val intent = Intent(this@MoneyInsertActivity, SupportActivity::class.java)
            startActivity(intent)
        }
    }

    fun selectData(
        folderid: Int
    ): ArrayList<String> {
        try {
            val dbHelper = DriveDBHelper(this, FOLDER_INFO, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val values = ContentValues()
            //select文　たった今insertした内容と一致するもののfolderidのみ受け取る
            val sql =
                "SELECT * FROM ${FOLDER_INFO} WHERE folderid=${folderid}"

            //クエリ実行 cursorで結果セット受け取り
            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                Log.d("該当件数1のはず：", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    payerList.add(cursor.getString(3))//memberの値を入れていく
                    payerList.add(cursor.getString(4))
                    payerList.add(cursor.getString(5))
                    payerList.add(cursor.getString(6))
                    payerList.add(cursor.getString(7))
                    payerList.add(cursor.getString(8))
                    cursor.moveToNext()
                }
            }
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
        }
        //メンバー全員入れる
        return payerList
    }//selectData閉じ

    //    うまくいかないinsertPara
    fun insertPara(folderid: Int, paraName: String, paraCost: Int, payer: String) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase

            val values = ContentValues()
            values.put("folderid", folderid) //代入したい項目,変数 とりあえずdatabaseに合わせて3
            values.put("paraName", paraName)//項目Spinnerダイアログで選択された値
            values.put("paraCost", paraCost) //入力金額
            values.put("payer", payer)        //負担者Spinnerダイアログで選択された値
            //ParagraphInfoテーブルにinsert
            val result = database.insertOrThrow(PARAGRAPH_INFO, null, values)
            //resultで成功か失敗かif文判断
            //入力した中身を確認
            Log.d(
                "insertした中身", "folderid:${folderid} paraName:${paraName} paraCost:${paraCost} " +
                        "payer:${payer}"
            )
        } catch (exception: Exception) {
            Log.e("InsertData", exception.toString())
        }
    }
}
////////////////////////////メモ↓//////////////////////////////////////////////////////////////////////////////////
