/*
* FolderInfoテーブル用
* 作成者：笛木
* 更新日：20201120
* 状況：Helperをファイル名と統一、ParagraphDBがうまくいかないのでここでparagraphInfoもつくる？
* */
package com.example.driveandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//DriveDBHelper定義
class DriveDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, databaseName, factory, version) {
    //データベース初期作成イベント
    override fun onCreate(database: SQLiteDatabase?) {

        database?.execSQL("CREATE TABLE IF NOT EXISTS FolderInfo(folderid integer primary key autoincrement, title text(10), date numeric not null, member1 text(10) not null, member2 text(10) default null, member3 text(10) default null, member4 text(10) default null, member5 text(10) default null, member6 text(10) default null)");
        //うまくいかない↓
        //database?.execSQL("CREATE TABLE IF NOT EXISTS ParagraphInfo( folderid integer not null,　paraNum integer primary key autoincrement, paraName TEXT(10) not null, paraCost integer not null, payer TEXT(10) not null,　FOREIGN KEY(folderid) REFERENCES FolderInfo(folderid))")

    }

    //更新イベント
    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table drivedb add column deleteFlag integer default 0")
            //database?.execSQL("alter table drivedb add column deleteFlag integer default 0")
        }
    }
}







