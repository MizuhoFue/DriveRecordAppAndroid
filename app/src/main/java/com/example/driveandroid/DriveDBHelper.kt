/*
* FolderInfoテーブル用
* 作成者：笛木
* 更新日：20201112
* 状況：TEXTの文字数を100から10に変更
* */
package com.example.driveandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val dbName: String = "drivedb"
private val tableName1: String = "FolderInfo"
private val dbVersion: Int = 1
private val tableName2: String = "ParagraphInfo"

//DriveDBHelper定義
private class FolderInfoDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, databaseName, factory, version) {
    //データベース初期作成イベント
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("CREATE TABLE IF NOT EXISTS FolderInfo(folderid integer primary key autoincrement, title text(10), date numeric not null, member1 text(10) not null, member2 text(10) default null, member3 text(10) default null, member4 text(10) default null, member5 text(10) default null, member6 text(10) default null)");
    }

    //更新イベント
    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table drivedb add column deleteFlag integer default 0")
        }
    }
}

//        //insert用文FolderInfo用　
//        fun insertData(
//            folderid: Int, title: String, date: Int, member1: String,
//            member2: String, member3: String, member4: String, member5: String, member6: String
//        ) {
//            try {
//                val dbHelper = DriveDBHelper(applicationContext, dbName, null, dbVersion)
//                val database = dbHelper.writableDatabase
//                //初期データをinsertしたい
//                val values = ContentValues()
//                values.put("title", “タイトル”)
//                values.put("20201019", date)
//                values.put("太郎", member1) //逆
//                //クエリ実行？
//                database.insertOrThrow(tableName1, null, values)
//
//            } catch (exception: Exception) {
//                Log.e("InsertData", exception.toString())
//            }
//        }






