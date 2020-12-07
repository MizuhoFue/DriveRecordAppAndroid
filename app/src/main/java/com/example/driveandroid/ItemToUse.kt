package com.example.driveandroid

data class ItemToUse(
    //配列初期化
    val folderid: Int, //id
    val paraNum: Int, //paraNum
    var paraNames: String, //使用項目名
    var paraCosts: Int, //金額表示
    var payers: String //負担者表示
)