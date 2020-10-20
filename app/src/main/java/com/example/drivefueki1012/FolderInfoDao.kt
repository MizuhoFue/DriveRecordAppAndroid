package com.example.drivefueki1012

import androidx.lifecycle.LiveData
import androidx.room.*

//FolderInfoのDAOクラス一覧

@Dao
interface FolderInfoDao {


    //データ取得
    @Query("SELECT * FROM FolderInfo")
    suspend fun selectAllFolderInfo(): List<FolderInfo> //Listとの違いは？

    @Insert
    suspend fun insert(folderinfo : FolderInfo)

    @Update
    suspend fun update(folderinfo:FolderInfo)

    @Delete
    suspend fun delete(folderinfo:FolderInfo)


}