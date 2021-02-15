package com.example.driveandroid

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
import com.example.driveandroid.Constants.Companion.PARAGRAPH_INFO
import kotlinx.android.synthetic.main.activity_folder_create.*
import kotlinx.android.synthetic.main.activity_folder_detail.*
import kotlinx.android.synthetic.main.activity_money_insert.*
import kotlinx.android.synthetic.main.item_to_use.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoneyInsertActivity : AppCompatActivity() {

    private var payer = ""  //負担者名、項目ごとに異なる可能性あり
    private var paraName = ""   //項目名　登録分によって複数あり
    private var paraCost = 0    //項目の金額　登録分によって複数あり
    private var paraCostArray = arrayListOf<Int>()       //全ての項目の金額合計

    // 負担者スピナーの配列　selectFolder内で初期化に変更　アダプター使用　
    //カメラ許可用コード準備
    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_insert)

        settings.setOnClickListener {
            val intent = Intent(this@MoneyInsertActivity, SupportActivity::class.java)
            startActivity(intent)
        }
        //FolderDetail、FolderCreateから渡されたfolderidを変数に入れる
        val folderId =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: -1 //-1の場合はエラーを出す
        //FolderDetail、FolderCreateのどちらから遷移したかのfromActivityを変数に入れる
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？
        Log.d("受け渡されたfolderid", "$folderId")
        Log.d("どこからMoneyInsertに遷移", fromActivity)
        //ツールバーの×を押したときのメッセージを確定させる FolderCreateからの遷移の場合は「ホーム画面に戻る」/Detailからの場合は「詳細に戻る」
        val toolbarTitle =
            if (fromActivity.equals(FolderCreateActivity::class.java.simpleName)) "金額項目を登録せずにホーム画面に戻りますか？" else "金額項目を登録せずに詳細画面に戻りますか？"
        //このタイミングでidを元にselect  selectメソッド内で空チェックを行う予定 nullなし配列をpayerListに格納
        val payerList: List<String> = selectData(folderId)

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
                Log.d("paraNameの値", paraName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ignore
            }
        }

        //アダプターに負担者配列リストを設定 payersはList<String> nullがあると怒られる
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payerList)
        //Spinnerにアダプター設定
        payerSpinner.adapter = adapter

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
                Log.d("payerの値", payer)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ignore
            }
        }

        //入力完了を押した際にダイアログを表示
        inputComp.setOnClickListener {//押したら
            Log.d("inputClick", "$paragraphSpinner")
            //id:costで入力された数値を文字にして数値にする nullと空文字、スペースが入っているとtrue
            if (!cost.text.isNullOrEmpty()) {
                paraCost = cost.text.toString().toInt()
                Log.d("項目の金額", "$paraCost")

//                //変化する値宣言
//                //金額表示用
//                var totalCost = 0
//
//                adapter.getCostValueListener(object : FolderDetailAdapter.CostValueListener {
//                    override fun costValue(view: View, paraCost: Int) {
//                        Log.d("受け取ったparaCost表示", "$paraCost")
//                        paraCostArray.add(paraCost)
//                        Log.d("paraCostArrayの中身", "$paraCostArray")
//                        //合計金額をtotalCostに格納
//                        totalCost = paraCostArray.sum()
//                        Log.d("合計金額", "$totalCost") //確認
//
//                        if (paraCostArray.sum() > 999999) {
//                            val dialog =
//                                AlertDialog.Builder(
//                                    this@MoneyInsertActivity,
//                                    R.style.MyAlertColor
//                                )
//                                    .setMessage("上限100万円を超えています。再入力してください。")
//                                    .setPositiveButton("OK") { _, _ ->
//                                    }
//                                    .create()
//                            dialog.show()
//                        }
//                    }
//                })

                //6桁超えてエラーダイアログ表示後
                if (paraCost > 999999) {
                    return@setOnClickListener
                }

                //ダイアログのメッセージ、各ボタンの処理を設定　「詳細」/「ホーム」遷移、入力修正のための「キャンセル」　
                AlertDialog.Builder(this, R.style.MyAlertColor)
                    .setMessage("どちらに移動しますか？")
                    .setPositiveButton(
                        "詳細",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                            //insert処理を入れる
                            insertPara(folderId, paraName, paraCost, payer)
                            //FolderCreate→Moneyの場合：startしてfinish()　　
                            // FolderDetail→MoneyInsertの場合：finish()のみ
                            //Createからの遷移の場合
                            if (fromActivity.equals(FolderCreateActivity::class.java.simpleName)) {
                                val intent = Intent(
                                    this@MoneyInsertActivity,
                                    FolderDetailActivity::class.java
                                )
                                intent.putExtra(EXTRA_FOLDERID, folderId)
                                intent.putExtra(
                                    EXTRA_ACTIVITYNAME,
                                    MoneyInsertActivity::class.java.simpleName
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
                            //insert処理を入れたい
                            insertPara(folderId, paraName, paraCost, payer)
                            val intent =
                                Intent(
                                    this@MoneyInsertActivity,
                                    FolderListActivity::class.java
                                )
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
            //エラーダイアログ、エラーを確認して入力内容破棄、再入力させる
            else {
                AlertDialog.Builder(this, R.style.MyAlertColor)
                    .setMessage("金額を入力してください")
                    .setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener { _, _ ->
                            //ignore
                        })
                    .show()
            }
        }

        //カメラボタンをクリックするとそのままカメラ起動
        camera.setOnClickListener {
            // カメラ機能を実装したアプリが存在するかチェック
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                if (checkPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
        }

        //ナビゲーションアイテムのリスナー
        close_button1.setOnClickListener {
            // BuilderからAlertDialogを作成 はいといいえの場所を入れ替え=処理入れ替え
            val dialog = AlertDialog.Builder(this, R.style.MyAlertColor)
                .setTitle(toolbarTitle) // タイトルセット
                .setPositiveButton(R.string.no) { _, _ -> // いいえ
                    Intent(this@MoneyInsertActivity, this::class.java)
                }
                .setNegativeButton(R.string.yes) { _, _ -> //はい
                    //moveTaskToBack(true)
                    finish()
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        //costを数える 文字数オーバーはエラーダイアログ
        cost.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //キーボードを閉じる
                val inputManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    MoneyInsert.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )

                (v as? EditText)?.also { editText ->
                    if (editText.text.length > 6) {
                        val dialog =
                            AlertDialog.Builder(this@MoneyInsertActivity, R.style.MyAlertColor)
                                .setMessage("上限100万円を超えています。再入力してください。")
                                .setPositiveButton("OK") { _, _ ->
                                    //OK押したら中身削除
                                    editText.text.clear()
                                    //次の項目にフォーカスが移らないようにする
                                    editText.requestFocus(View.FOCUS_UP)
                                }
                                .create()
                        dialog.show()
                    }
                }
            }
        }
    }

    /////////カメラ用メソッド////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d("メッセージ", "カメラ保存できた")

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put("_data", currentPhotoPath)
            }
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
            )
        }
    }

    //カメラ許可 二個目の許可をWRITE・・・・に変更
    private fun checkPermission(): Boolean {
        val cameraPermission = PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
        val extraStoragePermission = PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) //端末の設定を返す
        return cameraPermission && extraStoragePermission
    }

    private fun takePicture() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.driveandroid",
                it
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            CAMERA_PERMISSION_REQUEST_CODE
        )

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPAN).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    //ダイアログの結果を受け取りはいかいいえを判定してisGrantedのtrue/falseを入れる
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var isGranted = true
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false
                    }
                }
            } else {
                isGranted = false
            }
        } else {
            isGranted = false
        }
        if (isGranted) takePicture()
    }
/////////////////カメラ用メソッド終わり/////////////////////////////////////
    /**
     * @param folderId idを元にセレクト
     * @return payerList:List<String> nullを排除した純粋な負担者のみの配列
     * */
    private fun selectData( // TODO FolderInfoデータクラスを使ってhelperにまとめる
        folderId: Int
    ): List<String> {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val values = ContentValues()
            //select文　たった今insertした内容と一致するもののfolderidのみ受け取る
            val sql =
                "SELECT * FROM $FOLDER_INFO WHERE folderid=$folderId"
            val payerList = arrayListOf<String>()//戻り値用を一つ用意
            //クエリ実行 cursorで結果セット受け取り
            val cursor = database.rawQuery(sql, null)
            if (cursor.count > 0) {
                Log.d("該当件数1のはず：", "${cursor.count}")
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    //nullチェックしながらmemberの値を戻り値用payerListに入れていく
                    //member1も含めてnullチェックなのでindexは3から8
                    for (index in 3..8) {
                        if (!cursor.getString(index).isNullOrBlank()) {
                            payerList.add(cursor.getString(index))
                        }
                    }
                    cursor.moveToNext()
                }//while文閉じ
            }
            cursor.close()
            return payerList //負担者配列をreturn
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return arrayListOf()
        }
    }//selectData閉じ

    //ParagraphInfoにinsert TODO ParagraphInfoデータクラスを使ってコンパクトに helperにまとめる
    private fun insertPara(folderId: Int, paraName: String, paraCost: Int, payer: String) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("folderid", folderId) //最初に受け取ったfolderid
            values.put("paraName", paraName)//項目Spinnerダイアログで選択された値
            values.put("paraCost", paraCost) //入力金額
            values.put("payer", payer)       //負担者Spinnerダイアログで選択された値
            //ParagraphInfoテーブルにinsert
            val result = database.insertOrThrow(PARAGRAPH_INFO, null, values)
            //resultで成功か失敗かif文判断?
            //入力した中身を確認
            Log.d(
                "insertした中身", "folderid:$folderId paraName:$paraName paraCost:$paraCost " +
                        "payer:$payer"
            )
        } catch (exception: Exception) {
            Log.e("InsertData", exception.toString())
        }
    }

    //背景タップでeditTextのフォーカスが外れるようにする
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        MoneyInsert.requestFocus()
        return super.dispatchTouchEvent(ev)
    }

    /** selectFolder folderidを元に該当データをFolderInfoテーブルからセレクト
     * @param folderId:Int
     * @return ArrayList<FolderInfo>
     * */
    private fun selectFolder(folderId: Int): ArrayList<FolderInfo>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderList = ArrayList<FolderInfo>() //FolderInfo型の箱をつくる
            //select文　FolderInfoテーブルセレクト
            val sql = "SELECT * FROM $FOLDER_INFO WHERE folderid=$folderId" //sql文OK
            Log.d("SQL実行", sql)
            //cursorに結果をいれて
            val cursor =
                database.rawQuery(sql, null)
            Log.d("該当件数1のはず", "${cursor.count}")
            cursor.moveToFirst()
            if (cursor.count > 0) {
                while (!cursor.isAfterLast) {

                    val folderInfo = FolderInfo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor?.getString(4),
                        cursor?.getString(5),
                        cursor?.getString(6),
                        cursor?.getString(7),
                        cursor?.getString(8)
                    )
                    folderList.add(folderInfo)
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return folderList
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }

    /**selectPara folderidを元に該当データをParagraphInfoテーブルからセレクト
     *
     *@return : ArrayList<ItemToUse>
     *     ItemToUse型の箱をつくる
     * */
    private fun selectPara(folderId: Int): ArrayList<ItemToUse>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderDetail = ArrayList<ItemToUse>()
            //select文　ParagraphInfoテーブルセレクト
            val sql = "SELECT * FROM $PARAGRAPH_INFO WHERE folderid=$folderId" //sql文OK
            Log.d("SQL実行", sql)
            //cursorに結果をいれて
            val cursor =
                database.rawQuery(sql, null)
            Log.d("該当件数1のはず", "${cursor.count}")
            cursor.moveToFirst()
            if (cursor.count > 0) {
                while (!cursor.isAfterLast) {
                    val paragraphInfo = ItemToUse(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                    folderDetail.add(paragraphInfo) //箱に型を入れる
                    cursor.moveToNext()
                }
            }
//            else {
//                //paragraphinfoの中身が空の場合、金額項目の画面を表示しない
//                car_line.visibility = View.GONE
//                usedParagraph.visibility = View.GONE
//                folderDetailView.visibility = View.GONE
//                total.visibility = View.GONE
//                per.visibility = View.GONE
//            }
            cursor.close()
            return folderDetail
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }
}