//FolderInfoデータクラスfolderidをnullableにする？
package com.example.driveandroid

data class FolderInfo(
    val folderId: Int,
    val title: String,
    val date: Int,
    val member1: String,
    val member2: String?,
    val member3: String?,
    val member4: String?,
    val member5: String?,
    val member6: String?
)