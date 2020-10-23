/*
* DBのテーブル
*Room用なのでいらなくなった
* 更新日：2020年10月15日
* 作成者：笛木瑞歩
* */

package com.example.driveandroid

import android.icu.lang.UCharacter
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*


//FolderInfoテーブル
@Entity
class FolderInfo (//引数あとで考える?{}

    @PrimaryKey(autoGenerate = true)
    val folderid: Int =1, //or  folderId: Int?=null  フォルダーID

    @ColumnInfo(name="title")
    val title: String, //タイトル名
    @ColumnInfo(name="date")
    val date: Int, //NUMERICにしたい 日付

    @ColumnInfo(name="member1")
    val member1: String,  //NotNullにしたい　メンバー1

    @ColumnInfo(name="member2")
    val member2: String?=null, //default Nullにしたい以下同様
    @ColumnInfo(name="member3")
    val member3: String?=null,
    @ColumnInfo(name="member4")
    val member4: String?=null,
    @ColumnInfo(name="member5")
    val member5: String?=null,
    @ColumnInfo(name="member6")
    val member6: String?=null

)

//ParagraphInfoテーブル
@Entity
class ParagraphInfo (

    //foreignkeyどうする
    // @ForeignKey　val folderid : Int

    // @ColumnInfo(autoGenerate = true)  オートインクリメント、NotNullにしたい　項目番号
    val paranum: Int = 0, //or Int?=null

    val paraname: String, //NotNullにしたい 項目名

    val paracost: Int = 0, //orInt?=null NotNullにしたい　項目ごとの金額

    val repayer: String //NotNullにしたい　負担者

)



//@Dao
//interface ParagraphInfoDao{

    //データ取得
  //  @Query("SELECT * FROM ParagraphInfo")
   // fun selectAllParagraphInfo():List<ParagraphInfo>
//}

