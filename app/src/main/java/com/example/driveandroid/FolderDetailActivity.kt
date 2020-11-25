/*画面：フォルダ詳細
*更新日：2020年11月18日
*更新者：笛木瑞歩
*前回からの変更：18行目からMoneyInsertに受け渡す値、DBセレクトに必要な値を設定
*ここでセレクトしたidをMoneyInsertに遷移したときに渡す
* 戻る動作にも入れる？
*/
package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.driveandroid.Constants.Companion.EXTRA_ACTIVITYNAME
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.activity_folder_detail.*

class FolderDetailActivity : AppCompatActivity() {

    //FolderDetailから送るfolderidとして仮データ代入 本来はFolderListから引っ張られてくる
    private var folderid = 0

    //DB用変数用意
    private var date = 2020 / 11 / 12 //日付（仮）
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_detail)

        //FolderListから渡されたfolderidを変数に入れる
        val intent = getIntent()
        val folderid =
            intent.extras?.getInt(EXTRA_FOLDERID) ?: 0 // 0の場合はMoneyInsert自体できないようにするか
        Log.d("受け取ったfolderid","${folderid}")

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