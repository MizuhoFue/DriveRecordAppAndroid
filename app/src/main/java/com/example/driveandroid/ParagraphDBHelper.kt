/*
* このファイルでParagraphInfoテーブル作成、insert、delete関数を設定
*DriveDBHelperからParagraphInfoのものだけ移動
* * 作成者：笛木
* 更新日：20201109
* */
package com.example.driveandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val dbName: String = "drivedb"
private val dbVersion: Int = 1
private val tableName2: String = "ParagraphInfo"

private class ParagraphInfoDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, databaseName, factory, version) {
    //データベース初期作成イベント
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("CREATE TABLE IF NOT EXISTS ParagraphInfo( folderid integer not null,　paraNum integer primary key autoincrement, paraName TEXT(30) not null, paraCost integer not null, payer TEXT(30) not null,　FOREIGN KEY(folderid) REFERENCES FolderInfo(folderid))")
    }

    //更新イベント
    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table drivedb add column deleteFlag integer default 0")
        }
    }
}
