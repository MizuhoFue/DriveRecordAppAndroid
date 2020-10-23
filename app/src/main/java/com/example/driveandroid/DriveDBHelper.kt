package com.example.driveandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DriveDBHelper(context: Context, databaseName:String, factory: SQLiteDatabase.CursorFactory?, version:Int):
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

