/*
* 値を受け渡す際のキー設定をここにまとめる
* */
package com.example.driveandroid

class Constants {

    companion object {
        const val EXTRA_FOLDERID = "extra_folderid"
        const val EXTRA_ACTIVITYNAME = "extra_foldername"
        //DB用変数用意
        const val DB_NAME: String = "drivedb"  //DB名
        const val DB_VERSION: Int = 1  //変わることはあるのか
        const val FOLDER_INFO = "FolderInfo"
        const val PARAGRAPH_INFO = "ParagraphInfo"    //テーブル名
    }
}