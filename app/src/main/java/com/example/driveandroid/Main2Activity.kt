package com.example.driveandroid

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Main2Activity : AppCompatActivity() {
    private val dbName: String = "drivedb"
    private val tableName: String = "folderinfo"
    private val dbVersion: Int = 1

    private class DriveDBHelper(
        context: Context,
        databaseName: String,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, databaseName, factory, version) {

        //初期作成
        override fun onCreate(database: SQLiteDatabase?) {
            database?.execSQL("create table if not exists folderinfo (folderid integer PRIMARY KEY autoincrement, title TEXT(100), date numeric NOT NULL, member1 text(30) NOT NULL, member2 text(30), member3 text(30), member4 text(30), member5 text(30), member6 text(30))");
        }

        //更新時
        override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if (oldVersion < newVersion) {
                database?.execSQL("alter table folderinfo add column deleteFlag integer default 0")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_create)
        try {
            val dbHelper = DriveDBHelper(
                applicationContext,
                dbName,
                null,
                dbVersion
            )
            val database = dbHelper.writableDatabase


            val values = ContentValues()
            values.put("title", "タイトル")
            values.put("date", "20201010")
            values.put("member1","神田太郎")
            values.put("member2", "シム次郎")
            values.put("member3", "秋葉三郎")
            values.put("member4", "新橋花子")
            values.put("member5", "渋谷さくら")
            values.put("member6", "千代田葉子")

            val result = database.insertOrThrow(tableName, null, values)
            if (result == (0).toLong()) {
                Log.d("", "失敗")
            } else {
                Log.d("", "成功")
            }

        }catch(exception: Exception) {
            Log.e("insertData", exception.toString())
        }
    }

}

