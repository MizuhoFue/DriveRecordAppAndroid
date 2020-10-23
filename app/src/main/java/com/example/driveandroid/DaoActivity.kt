/*
* このファイルでDB作成、テーブル作成→サンプルデータinsertを行いたい
* 作成者：笛木
* 更新日：20201019
* */

package com.example.driveandroid

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
<<<<<<< HEAD:app/src/main/java/com/example/drivefueki1012/DaoActivity.kt
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.folder_create.*


class DaoActivity : AppCompatActivity() {


	private val dbName: String = "drivedb"
	private val tableName: String = "FolderInfo"
	private val dbVersion: Int = 1
=======
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_folder_detail.*

class DaoActivity : AppCompatActivity() {

    //DB名
    private val dbName: String = "drivedb"
    //FolderInfo テーブル名
    private val tableName: String = "FolderInfo"
    private val dbVersion: Int = 1
    //ParagraphInfo テーブル名　
    private val tableName2: String="ParagraphInfo"
>>>>>>> feature/camera:app/src/main/java/com/example/driveandroid/DaoActivity.kt

	//DriveDBHelper定義
	private class DriveDBHelper(
		context: Context,
		databaseName: String,
		factory: SQLiteDatabase.CursorFactory?,
		version: Int
	) :
		SQLiteOpenHelper(context, databaseName, factory, version) {


		//データベース初期作成イベント
		override fun onCreate(database: SQLiteDatabase?) {

			database?.execSQL("CREATE TABLE IF NOT EXISTS FolderInfo(folderid integer primary key autoincrement ,title text(100), date numeric not null,member1 text(30) not null ,member2 text(30) default null,member3 text(30) default null,member4 text(30) default null,member5 text(30) default null, member6 text(30) default null)")

        }

<<<<<<< HEAD:app/src/main/java/com/example/drivefueki1012/DaoActivity.kt
		//更新イベント
		override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
			if (oldVersion < newVersion) {
=======
//
//        override fun onCreate(database:SQLiteDatabase?){
//
//            database?.execSQL("CREATE TABLE IF NOT EXISTS ParagraphInfo(FOREIGN KEY(folderid) references FolderInfo(folderid)");
//        }
//

        //更新イベント
        override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if (oldVersion < newVersion) {
>>>>>>> feature/camera:app/src/main/java/com/example/driveandroid/DaoActivity.kt

				database?.execSQL("alter table drivedb add column deleteFlag integer default 0")

			}
		}


<<<<<<< HEAD:app/src/main/java/com/example/drivefueki1012/DaoActivity.kt
	}
=======
>>>>>>> feature/camera:app/src/main/java/com/example/driveandroid/DaoActivity.kt

    }

	//最初に実行
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_dao)

       // var title: EditText
       // var date: EditText

        //title = this.findViewById<EditText>(R.id.title)
       // date = this.findViewById<EditText>(R.id.date)
       // var member1 : EditText = this.findViewById<EditText>(R.id.member1)

		//val title = findViewById(R.id.title) as EditText



<<<<<<< HEAD:app/src/main/java/com/example/drivefueki1012/DaoActivity.kt
        //insert用文
=======
                    val dbHelper =
                        DriveDBHelper(
                            applicationContext,
                            dbName,
                            null,
                            dbVersion
                        )
                    val database = dbHelper.writableDatabase
                    //初期データをinsertしたい
                    val values = ContentValues()
                    values.put("title","title")
                    values.put("date","20201020")
                    values.put("member1","太郎")
                    //クエリ実行？
>>>>>>> feature/camera:app/src/main/java/com/example/driveandroid/DaoActivity.kt


		try {

			val dbHelper = DriveDBHelper(applicationContext, dbName, null, dbVersion)
			val database = dbHelper.writableDatabase
			//初期データをinsertしたい
			val values = ContentValues()
			values.put("title", "")
			values.put("date", "")
			values.put("member1", "")
			//クエリ実行？

			val result = database.insertOrThrow(tableName, null, values)
			if (result == (0).toLong()) {
				Log.d("", "失敗")
			} else {
				Log.d("", "成功")
			}


		} catch (exception: Exception) {
			Log.e("InsertData", exception.toString())

		}

	}


}







