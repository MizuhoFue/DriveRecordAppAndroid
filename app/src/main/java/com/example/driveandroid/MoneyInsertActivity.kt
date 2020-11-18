/*FolderCreate、FolderDetailから遷移
*画面名：MoneyInsertActivity 金額入力画面　使用した金額や使用用途の入力・登録を行う　ひとまずMainActivityを参考につくって部品化　
*整理：入力された値を変数に入れてSQL実行　一度に登録できるのは1項目　負担者参照する場合folderidで指定する感じになりそう
* 　　　遷移前のFolderListActivityから送ってもらう必要あり
*
*遷移先：FolderList,FolderDetail ダイアログで選択、遷移する
*ボタンメモ：入力完了：id:inputComp　
*
* やること:ダイアログ遷移を入れる→入力完了を押して遷移先決めたらinsert呼び出し、登録して遷移、データベース接続、　とりあえず選択された値を変数に入れられるようにするのが先？
* Numberの値変数に入れるのはすぐできそうな感じ ダイアログに「キャンセル」追加
* 更新者：笛木
* 更新日：2020年11月18日
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
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.activity_money_insert.*

class MoneyInsertActivity : AppCompatActivity() {

    //DB用変数用意　ParagraphInfoテーブル操作
    private val dbName: String = "drivedb"  //DB名
    private val dbVersion: Int = 1  //変わることはあるのか
    private val tableName2: String = "ParagraphInfo"    //テーブル名
    private var payer = ""  //負担者名、項目ごとに異なる可能性あり
    private var paraName = ""   //項目名　登録分によって複数あり
    private var paraCost = 0    //項目の金額　登録分によって複数あり

    //負担者スピナーの配列　アダプター使用　仮データを配列に入れ　実装はデータベースから引っ張ってきたものを配列にいれる
    val payerList = arrayListOf<String>("神田太郎", "シム太郎", "秋葉三郎", "新橋花子", "渋谷さくら", "千代田葉子")

//    //カメラ準備 static役割
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

        //FolderDetail、FolderCreateから渡されたfolderidを変数に入れる
        val intent = getIntent()
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: 0 //valでいいのか？ 0を入れるは違う気もする
        Log.d("受け渡されたfolderid", "${folderid}")

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
        val Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payerList)
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
                //ダイアログ表示する
                //ダイアログのメッセージ、各ボタンの処理を設定　「詳細」/「ホーム」遷移、入力修正のための「キャンセル」　
                AlertDialog.Builder(this)
                    .setMessage("どちらに移動しますか？")
                    .setPositiveButton(
                        "詳細",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                            //insert処理を入れる
                            val dbHelper =
                                ParagraphInfoDBHelper(applicationContext, dbName, null, dbVersion)
                            val database = dbHelper.writableDatabase
                            val values = ContentValues()
                            values.put("folderid", folderid) //代入したい項目,変数
                            values.put("paraName", paraName)//項目Spinnerダイアログで選択された値
                            values.put("paraCost", paraCost) //入力金額
                            values.put("payer", payer)        //負担者Spinnerダイアログで選択された値
                            //val result = database.insertOrThrow(tableName2, null, values)
                            val intent =
                                Intent(this@MoneyInsertActivity, FolderDetailActivity::class.java)
                            startActivity(intent)
                            finish()
                        })
                    .setNegativeButton(
                        "ホーム",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                            //insert処理を入れる
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
        //else { エラーダイアログ、エラーを確認して入力内容破棄、再入力させる 後ほど付け足し
        //
        //            }
        //カメラボタンをクリックするとCameraActivityに遷移
        camera.setOnClickListener {
            val intentCamera = Intent(this@MoneyInsertActivity, CameraActivity::class.java)
            startActivity(intentCamera)
            //フィニッシュせずに遷移
        }

        //仮置き×ボタンexitの動作
        exit.setOnClickListener {
            val intent = Intent(this@MoneyInsertActivity, FolderListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

////////////////////////////メモ↓//////////////////////////////////////////////////////////////////////////////////

//    //各項目入力、変数に入れる
//   //ボタンを押したら入力チェック
//     insert用メソッドはonCreate外で定義しておいた方がよさそう
//    fun checkInsert(view: View) {
//
//        //チェック事項は　各変数の空白、不整値　種類は？　 模擬開発演習コードなどを参考
//
//
//    }
//
//    //問題なければinsert文呼び出し　みたいな　
//    fun ParaInsert() {
// }
//

