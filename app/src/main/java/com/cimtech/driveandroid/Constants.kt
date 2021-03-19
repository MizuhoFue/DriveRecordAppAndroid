/*
* 値を受け渡す際のキー設定、DB処理の共通文字列をまとめる
* 更新日：12月15日
* 更新者：笛木
* */
package com.cimtech.driveandroid

class Constants {
    companion object {
        const val EXTRA_FOLDER_ID = "extra_folder_id"
        const val EXTRA_ACTIVITY_NAME = "extra_folder_name"
        //DB用変数用意
        const val DB_NAME: String = "drivedb"  //DB名
        const val DB_VERSION: Int = 1  //変わることはあるのか
        const val FOLDER_INFO = "FolderInfo"
        const val PARAGRAPH_INFO = "ParagraphInfo"    //テーブル名
        //Listのdeleteメソッド用定数
        const val WHERE_ID = "folderid = ?"
        //DetailのparaNum定数
        const val WHERE_PARA_NUM = "paraNum = ?"
    }
}