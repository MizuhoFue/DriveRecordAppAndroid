/*画面：フォルダ詳細
*更新日：2020年12月3日
*更新者：笛木瑞歩
*前回からの変更：FolderInfoの該当データ表示、日付スラッシュ表示
*星野さんのもの参考
*/
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
import kotlinx.android.synthetic.main.activity_folder_detail.*

class FolderDetailActivity : AppCompatActivity() {

    //DB用変数用意
    //dateとmemberNumはonResumeでの初期化に変更
    private var title = "" //TODO 漢字文字化けする
    private var member1 = ""  //メンバー名
    private var member2 = ""
    private var member3 = ""
    private var member4 = ""
    private var member5 = ""
    private var member6 = ""
    private var paraName = ""   //項目名
    private var paraCost = 0    //項目の金額 項目によって異なる　
    private var perParsonCost = 0   //項目ごとの一人当たり金額
    private var payer = ""      //項目ごとの負担者名　項目によって異なる可能性あり
    var totalCost = 0       //全ての項目の金額合計
    var parParsonTotalCost = 0   //全ての合計金額をメンバー人数で割った一人当たりの金額

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //配列初期化 paragraphInfoの中身用
    val ItemToUseList = ArrayList<ItemToUse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)
    }

    //Resume処理
    override fun onResume() {
        super.onResume()

        //FolderListまたはMoneyInsertから渡されたfolderid、遷移元ファイル名を変数に入れる
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: -1 // 0だと0番目の配列と被るため-1に設定
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？
        Log.d("どこから遷移", fromActivity)
        Log.d("受け取ったfolderid", "$folderid")

        //FolderInfo全件セレクト
        //戻り値をfolderListにいれてそれぞれviewにセット
        val folderList: ArrayList<FolderInfo> = selectFolder(folderid)
        //日付をfolderListから取り出す
        val date = folderList.get(0).date
        //日付を一回stringにしてスラッシュいれる
        val strDate = "$date"
        //yyyy / MM / DDの形
        val slashDate = strDate.take(4) + "/" + strDate.substring(4, 6) + "/" + strDate.takeLast(2)
        dateView.text = "$slashDate"

        //タイトル表示
        titleView.text = folderList.get(0).title

        //memberNum0にする
        var memberNum = 0
        //member1表示
        //member1_View.text = folderList.get(0).member1
        member1 = folderList.get(0).member1
        if (member1.isNotBlank()) {
            memberNum++
            member1_View.text = member1
        }
        //null許容しているため文字列とする getにグレー波線 なんかいいやり方ありますか
        member2 = folderList.get(0).member2.toString()
        member3 = folderList.get(0).member3.toString()
        member4 = folderList.get(0).member4.toString()
        member5 = folderList.get(0).member5.toString()
        member6 = folderList.get(0).member6.toString()

        //登録されている場合はメンバー数変数をインクリメントして表示
        //登録されていない場合は領域をView.GONEで領域を詰める
        if (member2.isNotBlank()) {
            memberNum++
            member2_View.text = member2
        } else {
            member2_View.visibility = View.GONE
        }

        if (member3.isNotBlank()) {
            memberNum++
            member3_View.text = member3
        } else {
            member3_View.visibility = View.GONE
        }

        if (member4.isNotBlank()) {
            memberNum++
            member4_View.text = member4
        } else {
            member4_View.visibility = View.GONE
        }

        if (member5.isNotBlank()) {
            memberNum++
            member5_View.text = member5
        } else {
            member5_View.visibility = View.GONE
        }

        if (member6.isNotBlank()) {
            memberNum++
            member6_View.text = member6
        } else {
            member6_View.visibility = View.GONE
        }
        //メンバー数表示
        member_numView.text = "$memberNum"

        /////FolderInfoの中身表示終了//////////////
        //ParagraphInfoの表示↓
        //ユーザーリストでデーターを追加、仮データ反映
        val list = Array<String>(10) { "項目" }

        //配列を表示させる
        val adapter = FolderDetailAdapter(list) //仮データ代入
        val layoutManager = LinearLayoutManager(this)

        // アダプターとレイアウトマネージャーをセット folderDetailはRecyclerViewのid
        folderDetail.layoutManager = layoutManager
        folderDetail.adapter = adapter
        folderDetail.setHasFixedSize(true)

        //金額入力ボタン押したら
        moneyInsert.setOnClickListener {
            //Intent作成 FolderDetailフォルダ詳細からMoneyInsert金額入力に遷移
            val intent = Intent(this@FolderDetailActivity, MoneyInsertActivity::class.java)
            //このフォルダの追加項目なのでfolderidをMoneyInsertへ　finish処理用にActivity名も送る
            intent.putExtra(EXTRA_FOLDERID, folderid)
            intent.putExtra(EXTRA_ACTIVITYNAME, this::class.java.simpleName)
            startActivity(intent)
            //クリアタスク、フィニッシュなし・MoneyInsertで戻るボタンを押すと再び詳細が確認できるようになっている
        }

        setting.setOnClickListener {
            val intent = Intent(this@FolderDetailActivity, SupportActivity::class.java)
            startActivity(intent)
        }

        //ホームへ戻る
        home.setOnClickListener {//押したら
            finish()
        }
    }//onResume閉じ

    /** selectFolder folderidを元に該当データをFolderInfoテーブルからセレクト
     * @param folderid:Int
     * @return ArrayList<FolderInfo>
     * */
    private fun selectFolder(folderid: Int): ArrayList<FolderInfo> {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
//            this, DB_NAME, null, DB_VERSION
            val database = dbHelper.readableDatabase
            val folderList = ArrayList<FolderInfo>() //FolderInfo型の箱をつくる
            //select文　FolderInfoテーブルセレクト
            val sql = "SELECT * FROM $FOLDER_INFO WHERE folderid=$folderid" //sql文OK
            Log.d("SQL実行", sql)
            //cursorに結果をいれて
            val cursor =
                database.rawQuery(sql, null)
            Log.d("該当件数1のはず", "${cursor.count}")
            cursor.moveToFirst()
            if (cursor.count > 0) {
                while (!cursor.isAfterLast) {
                    val folderInfo = FolderInfo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                    )
                    folderList.add(folderInfo) //箱に型を入れる
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return folderList
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return arrayListOf() //これでいいかわからないけどnullはコンパイルエラーでreturnできなかった
        }
    }

    /**selectPara folderidを元に該当データをParagraphInfoテーブルからセレクト
     *
     *@return : ArrayList<ItemToUse>
     * */
    private fun selectPara(folderid: Int) {
        //別ブランチで処理を書く
    }
}