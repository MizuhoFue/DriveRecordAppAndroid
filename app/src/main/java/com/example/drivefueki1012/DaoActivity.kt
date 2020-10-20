/*
* このファイルでDB作成、テーブル作成→サンプルデータinsertを行いたい
* 作成者：笛木
* 更新日：20201019
* */

package com.example.drivefueki1012

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_folder_detail.*

class DaoActivity : AppCompatActivity() {


    private val dbName: String = "drivedb"
    private val tableName: String = "FolderInfo"
    private val dbVersion: Int = 1

    //DriveDBHelper定義
    private  class DriveDBHelper(context: Context, databaseName:String, factory: SQLiteDatabase.CursorFactory?, version:Int):
        SQLiteOpenHelper(context,databaseName, factory,version) {

        //データベース初期作成イベント
        override fun onCreate(database: SQLiteDatabase?) {

            database?.execSQL("CREATE TABLE IF NOT EXISTS FolderInfo(folderid integer primary key autoincrement ,title string, date integer,member1 string ,member2 string default null,member3 string default null,member4 string default null,member5 string default null, member6 string default null)");

        }

        //更新イベント
        override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if (oldVersion < newVersion) {

                database?.execSQL("alter table drivedb add column deleteFlag integer default 0")

            }
        }


    }



    //最初に実行
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dao)


            //insert用文
            fun insertData(
                folderid: Int, title: String, date: Int, member1: String,
                member2: String, member3: String, member4: String, member5: String, member6: String
            ) {

                try {

                    val dbHelper = DriveDBHelper(applicationContext, dbName, null, dbVersion)
                    val database = dbHelper.writableDatabase
                    //初期データをinsertしたい
                    val values = ContentValues()
                    values.put("旅行", title)
                    values.put("20201019", date)
                    values.put("太郎", member1)
                    //クエリ実行？
                    database.insertOrThrow(tableName, null, values)


                } catch (exception: Exception) {
                    Log.e("InsertData", exception.toString())

                }

            }


        }


    }




