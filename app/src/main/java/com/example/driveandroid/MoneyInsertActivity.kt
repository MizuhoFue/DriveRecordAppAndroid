/*FolderCreate、FolderDetailから遷移
*画面名：MoneyInsertActivity 金額入力画面　使用した金額や使用用途の入力・登録を行う　ひとまずMainActivityを参考につくって部品化　
*
* 整理：入力された値を変数に入れてSQL実行　一度に登録できるのは1項目　負担者参照する場合folderidで指定する感じになりそう
* 　　　遷移前のActivityから送ってもらう必要あり？　レイアウトはひとまず終わり遷移をひとまずやるべき
*
*遷移先：FolderList,FolderDetail ダイアログで選択、遷移する
*ボタンメモ：入力完了：id:inputComp　
*
* やること:ダイアログ遷移を入れる→入力完了を押して遷移先決めたらinsert呼び出し、登録して遷移、データベース接続、　とりあえず選択された値を変数に入れられるようにするのが先？
* Numberの値変数に入れるのはすぐできそうな感じ ダイアログに「キャンセル」追加
* */
package com.example.driveandroid

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_money_insert.*

class MoneyInsertActivity : AppCompatActivity() {
//    //カメラ準備 static役割
//    companion object {
//        const val CAMERA_REQUEST_CODE = 1
//        const val CAMERA_PERMISSION_REQUEST_CODE = 2
//    }

    //負担者スピナーSpinner用の仮の値をひとまず配列payerSpinnerに入れておく　実装はデータベースから引っ張ってきたものを配列にいれる
    //private var <> Array<payerSpinner>.onItemSelectedListener: AdapterView.OnItemSelectedListener
    //    get() {}
    //    set() {}

    //負担者スピナーの配列　アダプター使用　仮データを配列に入れる
    val payerList = arrayListOf<String>("神田太郎", "シム太郎", "秋葉三郎", "新橋花子", "渋谷さくら", "千代田葉子")

    //表示
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_insert)
        //項目スピナー設定 ダイアログ表示、選択項目Spinnerスペースへの表示ができない↓
        paragraphSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //選択された項目名を変数paraNameに代入
                val spinner1 = parent as? Spinner
                val paraName = spinner1?.selectedItem as? String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            //ignore
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
                val spinner2 = parent as? Spinner
                var payer = spinner2?.selectedItem as? String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            //ignore
        }

        //カメラボタンをクリックするとCameraActivityに遷移
        camera.setOnClickListener {

            val intentCamera = Intent(this@MoneyInsertActivity, CameraActivity::class.java)
            startActivity(intentCamera)

        }

        //入力完了を押した際にダイアログを表示
        inputComp.setOnClickListener {//押した
            //ダイアログのメッセージ、各ボタンの処理を設定　「詳細」/「ホーム」遷移、入力修正のための「キャンセル」　
            AlertDialog.Builder(this)
                .setMessage("どちらに移動しますか？")
                .setPositiveButton(
                    "詳細",
                    DialogInterface.OnClickListener { dialog, which ->      //なぜグレー波線ついてる？
                        //insert処理を入れる
                        //
                        val intent = Intent(
                            this@MoneyInsertActivity, FolderDetailActivity::class.java
                        )
                        startActivity(intent)
                    })
                .setNegativeButton(
                    "ホーム",
                    DialogInterface.OnClickListener { dialog, which ->//なぜグレー波線ついてる？
                        //insert処理を入れる
                        //
                        val intent =
                            Intent(this@MoneyInsertActivity, FolderListActivity::class.java)
                        startActivity(intent)   //こちらはfinish()処理を入れたほうがよいかも
                    })
                .setNeutralButton("キャンセル", DialogInterface.OnClickListener { dialog, which ->

                })
                .show() //またはcreate() ?
        }
    }
}

////////////////////////////メモ↓//////////////////////////////////////////////////////////////////////////////////
//    //DB用変数
//    private val dbName: String = "drivedb"
//    private val tableName2: String = "ParagraphInfo"
//    private val dbVersion: Int = 1 //これがよくわからない
//
//    //各入力項目変数用意
//    val paraName: String = "Spinnerで選択された項目名"
//    val paraCost: Int = 200 //入力された値段　
//    val repayer = "選択された負担者の値"
//
//
//    //各項目入力、変数に入れる
//   //ボタンを押したら入力チェック
//
//    fun checkInsert(view: View) {
//
//        //チェック事項は　各変数の空白、不整値　種類は？　 模擬開発演習コードなどを参考
//
//
//    }
//
//    //問題なければinsert文呼び出し　
//    fun ParaInsert() {
// }
//

