package com.example.driveandroid

data class ItemToUse(
    //配列初期化
    var paraNames:String, //使用項目名
    var costNames:String, //「金額」
    var paraCosts:Int, //金額表示
    var totalYen:String, //「円」
    var perParson:String, //「一人当たり」
    var perParsonCosts:Int, //金額表示
    var cost:String, //「円」
    var payerName:String, //「負担者」
    var payers:String //負担者表示
)