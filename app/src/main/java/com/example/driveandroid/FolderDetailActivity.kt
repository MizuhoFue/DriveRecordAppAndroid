/*画面：フォルダ詳細
*更新日：2020年11月25日
*更新者：笛木瑞歩
*前回からの変更：FolderListかMoneyInsertから渡される値をはじめに受け取る処理追加
*ここでセレクトしたidをMoneyInsertに遷移したときに渡す
* 戻る動作にも入れる？
*/
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.activity_folder_detail.*

class FolderDetailActivity : AppCompatActivity() {

    //FolderDetailから送るfolderidとして仮データ代入 本来はFolderListから引っ張られてくる
    private var folderid = 0

    //DB用変数用意
    private var date = 20201112 //日付（仮）
    private var title = "熱海旅行" //タイトル（仮）
    private var memberNum = 5    //人数（仮）
    private var member1 = ""    //メンバー名
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

    //配列初期化
    val ItemToUseList = ArrayList<ItemToUse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)
    }

    //Resume処理
    override fun onResume() {
        super.onResume()

        //FolderInfo全件セレクト
//        val selectResult = selectFolder()
//        //戻り値をそれぞれ配列に入れ　
//        val dateList = selectResult.first
//        val titleList = selectResult.second

        //ユーザーリストでデーターを追加、仮データ反映
        val list = Array<String>(10) { "項目" }

        //配列を表示させる
        val adapter = FolderDetailAdapter(list) //仮データ代入
        val layoutManager = LinearLayoutManager(this)

        // アダプターとレイアウトマネージャーをセット folderDetailはRecyclerViewのid
        folderDetail.layoutManager = layoutManager
        folderDetail.adapter = adapter
        folderDetail.setHasFixedSize(true)

        //FolderListまたはMoneyInsertから渡されたfolderid、遷移元ファイル名を変数に入れる
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: -1 // 0だと0番目の配列と被るため-1に設定
        val fromActivity =
            intent.extras?.getString(EXTRA_ACTIVITYNAME) ?: "" //""が入る場合はエラー？
        Log.d("どこから遷移", fromActivity)
        Log.d("受け取ったfolderid", "${folderid}")

        moneyInsert.setOnClickListener {//新規追加項目ボタン押したら
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
    }
}