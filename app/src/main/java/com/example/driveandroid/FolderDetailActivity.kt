/*画面：フォルダ詳細
*更新日：2020年12月9日
*更新者：笛木瑞歩
*前回からの変更：Createでスラッシュ入りにするのでこちらのスラッシュ表示処理を削除、selectFolderのdate部分をgetStringに
* memberに登録がなかった場合nullが入るように変更 isNotBlank→isNullOrBlankチェックに変更  変数名folderId キャメルケース チェック系メソッド化は別ブランチ
*星野さんのもの参考
*/
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.DB_NAME
import com.example.driveandroid.Constants.Companion.DB_VERSION
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import com.example.driveandroid.Constants.Companion.FOLDER_INFO
import com.example.driveandroid.Constants.Companion.PARAGRAPH_INFO
import kotlinx.android.synthetic.main.activity_folder_detail.*

class FolderDetailActivity : AppCompatActivity() {

    //DB用変数用意
    //FolderInfo関連変数はonResume内で初期化
    //以下RecyclerView用変数
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
        val folderId =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: -1 // 0だと0番目の配列と被るため-1に設定
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？
        Log.d("どこから遷移", fromActivity)
        Log.d("受け取ったfolderid", "$folderId")

        //FolderInfo全件セレクト
        //戻り値をfolderListにいれてそれぞれviewにセット nullableにしておく folderListの中身がnullでない場合は表示
        val folderList: ArrayList<FolderInfo>? = selectFolder(folderId)

        //日付をfolderListから取り出す
        val date = folderList?.get(0)?.date
        dateView.text = date //スラッシュ表示削除

        //タイトル表示
        val title = folderList?.get(0)?.title //TODO 漢字が文字化けする(例：熱海旅行の「海」)
        titleView.text = title

        //memberNum0にする メンバーが増えるとインクリメント
        var memberNum = 0
        //member1表示
        val member1 = folderList?.get(0)?.member1
        //member1のチェック省略してもよい？ 入力必須のdate、titleは上の方でチェックしていない
        if (!member1.isNullOrBlank()) { //?.　nullじゃない時にこの処理をやるよっていう書き方
            memberNum++
            member1_view.text = member1
        }

        //文字が入っていない場合はnullが入っている
        val member2 = folderList?.get(0)?.member2
        val member3 = folderList?.get(0)?.member3
        val member4 = folderList?.get(0)?.member4
        val member5 = folderList?.get(0)?.member5
        val member6 = folderList?.get(0)?.member6

        //登録されている場合はメンバー数変数をインクリメントして表示
        //登録されていない場合(null)は領域をView.GONEで領域を詰める

        if (!member2.isNullOrBlank()) {
            memberNum++
            member2_view.text = member2
        } else {
            member2_view.visibility = View.GONE
        }

        if (!member3.isNullOrBlank()) {
            memberNum++
            member3_view.text = member3
        } else {
            member3_view.visibility = View.GONE
        }

        if (!member4.isNullOrBlank()) {
            memberNum++
            member4_view.text = member4
        } else {
            member4_view.visibility = View.GONE
        }

        if (!member5.isNullOrBlank()) {
            memberNum++
            member5_view.text = member5
        } else {
            member5_view.visibility = View.GONE
        }

        if (!member6.isNullOrBlank()) {
            memberNum++
            member6_view.text = member6
        } else {
            member6_view.visibility = View.GONE
        }

        //メンバー数表示
        member_num_view.text = "$memberNum"

        //ユーザーリストでデーターを追加
        val folderDetail = selectPara(folderId)

        //配列を表示させる
        folderDetail?.let {
            val adapter = FolderDetailAdapter(it)
            val layoutManager = LinearLayoutManager(this)

            // アダプターとレイアウトマネージャーをセット folderDetailはRecyclerViewのid
            folderDetailView.layoutManager = layoutManager
            folderDetailView.adapter = adapter
            folderDetailView.setHasFixedSize(true)
            //インターフェース
            adapter.setOnItemClickListener(object : FolderDetailAdapter.OnItemClickListener {
                override fun onItemClickListener(view: View, deleteId: Int) {
                    Log.d("deleteIdとして受け取り", "$deleteId")
                    //ダイアログを出し、OKだったらdeleteする
                    val dialog = AlertDialog.Builder(this@FolderDetailActivity) //thisだとコンパイルエラー
                        .setMessage("選択した内容を削除してもいいですか?")
                        .setPositiveButton(R.string.yes) { _, _ ->
                            //deletePara呼び出し 該当データをParagraphInfoから削除
                            deletePara(deleteId)
                            //画面更新
                            onResume()
                        }.setNegativeButton(R.string.no) { _, _ ->
                            Log.d("いいえを選択", "いいえ")
                        }
                        .create() //show()だとクラッシュ
                    dialog.show()
                }

                //自動で生成された
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) = Unit
            })
        }

        moneyInsert.setOnClickListener {//新規追加項目ボタン押したら
            //Intent作成 FolderDetailフォルダ詳細からMoneyInsert金額入力に遷移
            val intent = Intent(this@FolderDetailActivity, MoneyInsertActivity::class.java)
            //このフォルダの追加項目なのでfolderidをMoneyInsertへ　finish処理用にActivity名も送る
            intent.putExtra(EXTRA_FOLDERID, folderId)
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
    } //onResume閉じ

    /** selectFolder folderidを元に該当データをFolderInfoテーブルからセレクト
     * @param folderId:Int
     * @return ArrayList<FolderInfo>
     * */
    private fun selectFolder(folderId: Int): ArrayList<FolderInfo>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderList = ArrayList<FolderInfo>() //FolderInfo型の箱をつくる
            //select文　FolderInfoテーブルセレクト
            val sql = "SELECT * FROM $FOLDER_INFO WHERE folderid=$folderId" //sql文OK
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
                        cursor.getString(2), //getStringに変更
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                    )
                    folderList.add(folderInfo)
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return folderList
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }

    /**selectPara folderidを元に該当データをParagraphInfoテーブルからセレクト
     *
     *@return : ArrayList<ItemToUse>
     *     ItemToUse型の箱をつくる
     * */
    private fun selectPara(folderId: Int): ArrayList<ItemToUse>? {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.readableDatabase
            val folderDetail = ArrayList<ItemToUse>()
            //select文　ParagraphInfoテーブルセレクト
            val sql = "SELECT * FROM $PARAGRAPH_INFO WHERE folderid=$folderId" //sql文OK
            Log.d("SQL実行", sql)
            //cursorに結果をいれて
            val cursor =
                database.rawQuery(sql, null)
            Log.d("該当件数1のはず", "${cursor.count}")
            cursor.moveToFirst()
            if (cursor.count > 0) {
                while (!cursor.isAfterLast) {
                    val paragraphInfo = ItemToUse(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                    folderDetail.add(paragraphInfo) //箱に型を入れる
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return folderDetail
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }

    /**deletePara folderidを元にParagraphInfoの該当データをdelete
     * @param deleteId
     * */
    private fun deletePara(deleteId: Int) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val whereClauses = "folderid = ?"
            val whereArgs = arrayOf(deleteId.toString())
            database.delete(PARAGRAPH_INFO, whereClauses, whereArgs)
            Log.d("deletePara通ったfolderid", "$deleteId")
        } catch (exception: Exception) {
            Log.d("deletePara", exception.toString())
        }
    }
}