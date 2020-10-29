package com.example.driveandroid
/*FolderCreate、FolderDetailから遷移
*画面名：MoneyInsertActivity 金額入力画面　使用した金額や使用用途の入力・登録を行う　ひとまずMainActivityを参考につくって部品化　
*
* 整理：入力された値を変数に入れてSQL実行　一度に登録できるのは1項目　負担者参照する場合folderidで指定する感じになりそう
* 　　　遷移前のActivityから送ってもらう必要あり？　ダイアログは後で　ひとまずDB接続を終わらせる　
*
*
*遷移先：FolderList,FolderDetail ダイアログで選択、遷移する
*ボタンメモ：入力完了：id:inputComp　
*
* やること:ダイアログ遷移を入れる　レイアウト（円だけそのまま）、データベース接続、　とりあえず選択された値を変数に入れられるようにするのが先？
* Numberの値変数に入れるのはすぐできそうな感じ
* */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MoneyInsertActivity : AppCompatActivity() {
        //負担者スピナーSpinner用の仮の値をひとまず配列hutannsyaに入れておく　実装はデータベースから引っ張ってきたものを配列にいれる
        private val hutannsya=arrayOf("神田太郎","シム太郎","秋葉三郎","新橋花子","渋谷さくら","千代田葉子")
    //表示を担っているイメージ
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_insert)


        //項目名はSpinner、ダイアログバージョンで選ぶようにする　部品を変数に割り当てる
        val koumokuSpinner=findViewById<Spinner>(R.id.koumokuSpinner)

        //選択肢を表示するためにはadapterが必要  変数用意  moneyinsert_spinnerのkoumokuSpinnerタグの文字列を使用、simple_spinner_itemらへんは用意されてる引数？
        val adapterKoumoku= ArrayAdapter.createFromResource(this,R.array.koumokuSpinner,android.R.layout.simple_spinner_item)

        //メソッドを使ってやってみよう〜（？）　
        adapterKoumoku.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //これはなんでしょうね　言語化できない
        koumokuSpinner.adapter=adapterKoumoku    //ダイアログ表示まで確認おっけ

        //負担者用Spinner設定　こちらはデータベースからもってくるから先にデータベースで仮データを登録する必要がありそう　
        val hutannsyaSpinner=findViewById<Spinner>(R.id.hutannsyaSpinner)

        //adapter生成　Spinnerごとにつくるっぽい ひとまず用意した配列hutannsyaを参照
        val adapterHutannsya=ArrayAdapter(this, android.R.layout.simple_spinner_item, hutannsya)

        //メソッドを使ってやってみよう〜（？）　
        adapterHutannsya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //これはなんでしょうね
        hutannsyaSpinner.adapter=adapterHutannsya //ダイアログ表示まで確認おっけ　　レイアウト崩れとるがあとでいいか・・・

        //入力完了ボタン（inputComp）を遷移用ボタンとして設定
        val inputButton: Button=findViewById<Button>(R.id.inputComp)

        inputButton.setOnClickListener{//押したら

            //Intent作成 金額入力からフォルダー詳細に遷移
            val intent = Intent(this@MoneyInsertActivity,
                FolderDetail::class.java)
            startActivity(intent)
        }

        //カメラボタン設定
        val camerabutton: Button=findViewById<Button>(R.id.camera)
        //クリックするとCameraActivityに遷移
        camerabutton.setOnClickListener{

            val intentcamera= Intent(this@MoneyInsertActivity,CameraActivity::class.java)
            startActivity(intentcamera)
        }



    }

    //DB用変数
    private val dbName: String = "drivedb"
    private val tableName2: String = "ParagraphInfo"
    private val dbVersion: Int=1 //これがよくわからない

    //各入力項目変数用意
    val paraName: String="Spinnerで選択された項目名"
    val paraCost:Int=200 //入力された値段　
    val repayer="選択された負担者の値"





    //各項目入力、変数に入れる











    //ボタンを押したら入力チェック

    fun checkInsert(view: View){

        //チェック事項は　各変数の空白、不整値　種類は？　 模擬開発演習コードなどを参考



    }

    //問題なければinsert文呼び出し　ここに定義というかinsertするのはこの画面だからひとまずベタ書きする
    fun ParaInsert(){





    }









}