/*このファイルでParagraphInfoテーブル作成
* DriveDBHelperからParagraphInfoのものだけ移動
* タスク：
* 作成者：笛木
* 更新日：20201112
* */
package com.example.driveandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val dbName: String = "drivedb"
private val dbVersion: Int = 1  //これがいまいちわからない
private val tableName2: String = "ParagraphInfo"

class ParagraphInfoDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, databaseName, factory, version) {
    //データベース初期作成イベント
    override fun onCreate(database: SQLiteDatabase?) {  //パラメータ名はAndroidstudio用にキャメル 全てのTEXT(10)に
        database?.execSQL("CREATE TABLE IF NOT EXISTS ParagraphInfo( folderid integer not null,　paraNum integer primary key autoincrement, paraName TEXT(10) not null, paraCost integer not null, payer TEXT(10) not null,　FOREIGN KEY(folderid) REFERENCES FolderInfo(folderid))")
    }

    //更新イベント
    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table drivedb add column deleteFlag integer default 0")
        }
    }
}
