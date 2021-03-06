/*画面：フォルダ詳細
*更新日：2020年12月28日
*更新者：笛木瑞歩
*/
package com.cimtech.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cimtech.driveandroid.Constants.Companion.DB_NAME
import com.cimtech.driveandroid.Constants.Companion.DB_VERSION
import com.cimtech.driveandroid.Constants.Companion.EXTRA_ACTIVITY_NAME
import com.cimtech.driveandroid.Constants.Companion.EXTRA_FOLDER_ID
import com.cimtech.driveandroid.Constants.Companion.FOLDER_INFO
import com.cimtech.driveandroid.Constants.Companion.PARAGRAPH_INFO
import com.cimtech.driveandroid.Constants.Companion.WHERE_PARA_NUM
import kotlinx.android.synthetic.main.activity_folder_create.*
import kotlinx.android.synthetic.main.activity_folder_detail.*
import kotlinx.android.synthetic.main.activity_folder_detail.close_button
import kotlinx.android.synthetic.main.activity_folder_detail.setting
import kotlinx.android.synthetic.main.activity_money_insert.*
import kotlinx.android.synthetic.main.item_to_use.*

class FolderDetailActivity : AppCompatActivity() {

    //DB用変数用意
    //FolderInfo関連変数はonResume内で初期化
    //以下RecyclerView用変数
    private var paraName = ""   //項目名

    //金額表示用
    private var paraCostArray = arrayListOf<Int>()       //全ての項目の金額合計

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //配列初期化 paragraphInfoの中身用
    val ItemToUseList = ArrayList<ItemToUse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)
    }

    override fun onUserLeaveHint() {
        //ホームボタンが押された時や、他のアプリが起動した時に呼ばれる
        //戻るボタンが押された場合には呼ばれない
        paraCostArray.clear()
    }

    //端末を再起動した際
    override fun onRestart() {
        super.onRestart()
        paraCostArray.clear()
    }

    //Resume処理
    override fun onResume() {
        super.onResume()

        //FolderListまたはMoneyInsertから渡されたfolderid、遷移元ファイル名を変数に入れる
        val folderId =
            intent.extras?.getInt(EXTRA_FOLDER_ID) ?: -1 // 0だと0番目の配列と被るため-1に設定
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITY_NAME) ?: "" //""が入る場合はエラー？
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
            //・と空のメンバーを非表示、領域詰め
            point2.visibility = View.GONE
        }

        if (!member3.isNullOrBlank()) {
            memberNum++
            member3_view.text = member3
        } else {
            point3.visibility = View.GONE
        }

        if (!member4.isNullOrBlank()) {
            memberNum++
            member4_view.text = member4
        } else {
            point4.visibility = View.GONE
        }

        if (!member5.isNullOrBlank()) {
            memberNum++
            member5_view.text = member5
        } else {
            point5.visibility = View.GONE
        }

        if (!member6.isNullOrBlank()) {
            memberNum++
            member6_view.text = member6
        } else {
            point6.visibility = View.GONE
        }

        //メンバー数表示
        member_num_view.text = "$memberNum"

        //ユーザーリストでデーターを追加
        val itemToUseList = selectPara(folderId)

        //配列を表示させる
        itemToUseList?.let {
            val adapter = FolderDetailAdapter(it, memberNum) //memberNumも引数とする
            val layoutManager = LinearLayoutManager(this)

            // アダプターとレイアウトマネージャーをセット folderDetailはRecyclerViewのid
            folderDetailView.layoutManager = layoutManager
            folderDetailView.adapter = adapter
            folderDetailView.setHasFixedSize(true)

            //変化する値宣言
            //金額表示用
            var totalCost = 0
            var parParsonTotalCost = 0
            total_value.text = totalCost.toString()
            total_per_value.text = parParsonTotalCost.toString()

            adapter.getCostValueListener(object : FolderDetailAdapter.CostValueListener {
                override fun costValue(view: View, paraCost: Int) {
                    Log.d("受け取ったparaCost表示", "$paraCost")
                    paraCostArray.add(paraCost)
                    Log.d("paraCostArrayの中身", "$paraCostArray")
                    //合計金額をtotalCostに格納
                    totalCost = paraCostArray.sum()
                    Log.d("合計金額", "$totalCost") //確認
                    //合計金額表示
                    total_value.text = "%,d".format(totalCost) //3桁ずつカンマ区切りにする
                    //合計金額の一人当たりを表示
                    parParsonTotalCost = totalCost / memberNum
                    Log.d("一人当たり", "$parParsonTotalCost")
                    total_per_value.text = "%,d".format(parParsonTotalCost) //カンマ区切り
                }
            })

            //インターフェース はいといいえの配置を入れ替え＝処理を入れ替え
            adapter.setOnItemClickListener(object : FolderDetailAdapter.OnItemClickListener {
                override fun onItemClickListener(view: View, deleteNum: Int, position: Int) {
                    Log.d("deleteNumとして受け取り", "$deleteNum")
                    //ダイアログを出し、OKだったらdeleteする
                    val dialog = AlertDialog.Builder(
                        this@FolderDetailActivity,
                        R.style.MyAlertColor
                    ) //thisだとコンパイルエラー
                        .setMessage("選択した内容を削除してもいいですか?")
                        .setNegativeButton(R.string.no) { _, _ ->
                            Log.d("いいえを選択", "いいえ")
                        }.setPositiveButton(R.string.yes) { _, _ -> //はいを選択
                            //deletePara呼び出し 該当データをParagraphInfoから削除
                            deletePara(deleteNum)
                            //画面からも該当データを消す,更新する
                            itemToUseList.removeAt(position)
                            Log.d("消したいposition", "$position")
                            paraCostArray.clear() //配列の中身リセット
                            adapter.notifyDataSetChanged()
                            Log.d("一度paraCostArrayの中身削除", "$paraCostArray")
                            //合計金額系各値0でリセット
                            totalCost = 0
                            parParsonTotalCost = 0
                            total_value.text = totalCost.toString()
                            total_per_value.text = parParsonTotalCost.toString()
                            //メッセージ表示の処理別ブランチでマージ済み
                        }
                        .create() //show()だとクラッシュ
                    dialog.show()
                }
            })
        }

        moneyInsert.setOnClickListener {//新規追加項目ボタン押したら
            //Intent作成 FolderDetailフォルダ詳細からMoneyInsert金額入力に遷移
            val intent = Intent(this@FolderDetailActivity, MoneyInsertActivity::class.java)
            //このフォルダの追加項目なのでfolderidをMoneyInsertへ　finish処理用にActivity名も送る
            intent.putExtra(EXTRA_FOLDER_ID, folderId)
            intent.putExtra(EXTRA_ACTIVITY_NAME, this::class.java.simpleName)
            startActivity(intent)
            //クリアタスク、フィニッシュなし・MoneyInsertで戻るボタンを押すと再び詳細が確認できるようになっている
            //paraCostをリセット
            paraCostArray.clear()
        }

        setting.setOnClickListener {
            val intent = Intent(this@FolderDetailActivity, SupportActivity::class.java)
            startActivity(intent)
            paraCostArray.clear()
        }

        //ナビゲーションアイテムのリスナー
        close_button.setOnClickListener {
            // BuilderからAlertDialogを作成 はい、いいえの配置を変えるため処理も入れ替え
            val dialog = AlertDialog.Builder(this, R.style.MyAlertColor)
                .setTitle(R.string.finish_message_money) // タイトル
                .setPositiveButton(R.string.yes) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.no) { _, _ ->
                    Intent(this@FolderDetailActivity, this::class.java)
                }
                .create()
            // AlertDialogを表示
            dialog.show()
        }

        //ホームへ戻る,作成完了する
        home.setOnClickListener {//押したら
            finish()
        }

        //共有ボタン
        share.setOnClickListener {
            //StringBuilderで文字連結
            val share = StringBuilder().apply {
                append("日付 $date \n")
                append("タイトル $title \n")
                append("メンバー名 \n")
                append("$member1 \n")
                if (!member2.isNullOrEmpty()) {
                    append("$member2 \n")
                }
                if (!member3.isNullOrEmpty()) {
                    append("$member3 \n")
                }
                if (!member4.isNullOrEmpty()) {
                    append("$member4 \n")
                }
                if (!member5.isNullOrEmpty()) {
                    append("$member5 \n")
                }
                if (!member6.isNullOrEmpty()) {
                    append("$member6 \n \n")
                }
                append("\n")
                //使用項目の配列
                itemToUseList?.forEach {
                    val totalParaCosts = "%,d".format(it.paraCosts.toInt())
                    //使用項目別の一人当たりの金額
                    val parParsonCost = "%,d".format(it.paraCosts.toInt() / memberNum)
                    append("使用項目 ${it.paraNames}\n")
                    append("金額 $totalParaCosts 円 \n")
                    append("1人当たり $parParsonCost 円\n")
                    append("負担者 ${it.payers} \n \n")
                }
                if (paraCostArray.sum() == 0) {
                    append("使用項目\n未登録\n\n")
                }
                val totalArray = "%,d".format(paraCostArray.sum())
                val totalParArray = "%,d".format(paraCostArray.sum() / memberNum)
                append("合計金額 $totalArray 円\n")
                append("1人当たり $totalParArray 円")
            }

            //共有処理
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, share.toString()
                )
                putExtra(EXTRA_ACTIVITY_NAME, this::class.java.simpleName)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            paraCostArray.clear()
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
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor?.getString(4),
                        cursor?.getString(5),
                        cursor?.getString(6),
                        cursor?.getString(7),
                        cursor?.getString(8)
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
//            else {
//                //paragraphinfoの中身が空の場合、金額項目の画面を表示しない
//                car_line.visibility = View.GONE
//                usedParagraph.visibility = View.GONE
//                folderDetailView.visibility = View.GONE
//                total.visibility = View.GONE
//                per.visibility = View.GONE
//            }
            cursor.close()
            return folderDetail
        } catch (exception: Exception) {
            Log.e("SelectData", exception.toString())
            return null
        }
    }

    /**deletePara folderidを元にParagraphInfoの該当データをdelete
     * @param deleteNum
     * */
    private fun deletePara(deleteNum: Int) {
        try {
            val dbHelper = DriveDBHelper(this, DB_NAME, null, DB_VERSION)
            val database = dbHelper.writableDatabase
            val whereArgs = arrayOf(deleteNum.toString())
            database.delete(PARAGRAPH_INFO, WHERE_PARA_NUM, whereArgs)
            Log.d("deletePara通ったparaNum", "$deleteNum")
        } catch (exception: Exception) {
            Log.d("deletePara", exception.toString())
        }
    }
}