/*FolderCreate、FolderDetailから遷移
*画面名：MoneyInsertActivity 金額入力画面　使用した金額や使用用途の入力・登録を行う　
*整理：入力された値を変数に入れてSQL実行　一度に登録できるのは1項目
*遷移先：FolderList,FolderDetail ダイアログで選択、登録して遷移、データベース接続
*ボタンメモ：入力完了：id:inputComp　設定:settings
*プレースホルダー、金額入力の空白チェック・エラー処理・ダイアログ、カメラを許可しなかった場合の処理
*ダイアログに「キャンセル」追加
*更新者：笛木
*更新日：2020年11月26日
* */
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
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_folder_create.drive_toolbar
import kotlinx.android.synthetic.main.activity_money_insert.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MoneyInsertActivity : AppCompatActivity() {

    private var payer = ""  //負担者名、項目ごとに異なる可能性あり
    private var paraName = ""   //項目名　登録分によって複数あり
    private var paraCost = 0    //項目の金額　登録分によって複数あり

    // 負担者スピナーの配列　アダプター使用　
    val payerList = arrayListOf<String>()

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
        val intent = getIntent()
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: -1 //-1の場合はエラーを出す
        //FolderDetail、FolderCreateのどちらから遷移したかのfromActivityを変数に入れる
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？

        Log.d("受け渡されたfolderid", "${folderid}")
        Log.d("どこから遷移", "{$fromActivity}")

        //このタイミングでidを元にselect  selectメソッド内で空チェックを行う予定
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

    //カメラ許可 TODO 許可しなかった場合の処理
    private fun checkPermission(): Boolean {
        val cameraPermission = PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
        val extraStoragePermission = PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var isGranted = true
        if (requestCode == MoneyInsertActivity.CAMERA_PERMISSION_REQUEST_CODE) {
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

        if (isGranted) {
            takePicture()
        } else {
            grantCameraPermission()
        }
    }

    /////////////////カメラ用メソッド終わり/////////////////////////////////////
    fun selectData( // TODO FolderInfoデータクラスを使ってhelperにまとめる
        folderid: Int
    ): ArrayList<String> {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
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

    //ParagraphInfoにinsert TODO ParagraphInfoデータクラスを使ってコンパクトに helperにまとめる
    fun insertPara(folderid: Int, paraName: String, paraCost: Int, payer: String) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("folderid", folderid) //最初に受け取ったfolderid
            values.put("paraName", paraName)//項目Spinnerダイアログで選択された値
            values.put("paraCost", paraCost) //入力金額
            values.put("payer", payer)       //負担者Spinnerダイアログで選択された値
            //ParagraphInfoテーブルにinsert
            val result = database.insertOrThrow(PARAGRAPH_INFO, null, values)
            //resultで成功か失敗かif文判断?
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