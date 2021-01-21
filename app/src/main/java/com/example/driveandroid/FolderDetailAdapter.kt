/**
 * 更新内容：FolderDetailでの金額表示系
 * 更新者：笛木
 * 更新日：2020年12月10日〜2020年12月15日 作業 金額表示系の処理追加
 * */
package com.example.driveandroid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_to_use.view.*

//金額配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderDetailAdapter(
    private val folderDetail: ArrayList<ItemToUse>, private val memberNum: Int//コンストラクタで引数宣言
) : RecyclerView.Adapter<FolderDetailAdapter.CustomViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener

    //項目金額をDetailに渡すリスナー準備
    lateinit var costListener: CostValueListener

    // ViewHolderクラス idスネークケースに変更
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val paraName = view.para_name
        val paraCostView = view.para_cost_view
        val perPerson_costView = view.per_parson_cost_view
        val payerView = view.payer_view
        val trash_box = view.trash_box
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_to_use, parent, false)
        return CustomViewHolder(item)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return this.folderDetail.size
    }

    // ViewHolderに表示するテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder) {
            paraName.text = folderDetail[position].paraNames
            paraCostView.text = folderDetail[position].paraCosts
            //項目金額計算、表示
            val paraCost = folderDetail[position].paraCosts.toInt()//項目金額を計算用にInt型にする
            val perParsonCost = paraCost / memberNum//項目一人当たりを出すためにmemberNumで割る
            perPerson_costView.text = perParsonCost.toString()
            Log.d("選択したところのparaCosts", "$paraCost")
            Log.d("人数で割った数字", "$perParsonCost")
            costListener.costValue(view, paraCost) //paraCostをDetailに送る
            payerView.text = folderDetail[position].payers
            //ひとまずログをだしておく
            Log.d("folderIdの中身をみておく", "${folderDetail[position].folderId}")
            //ゴミ箱クリック時　
            trash_box.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val deleteId = folderDetail[position].folderId //positionのfolderIdを取得
                    val deleteNum = folderDetail[position].paraNum //削除する項目
                    Log.d("deleteするidの値", "$deleteId")
                    listener.onItemClickListener(view, deleteNum, position)//FolderDetailのdeleteに送る
                }
            })
        }
    }

    //インターフェース作成 listenerにviewとdeleteIdを持たせる
    interface OnItemClickListener {
        fun onItemClickListener(view: View, deleteId: Int, position: Int)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    //paraCostをDetailに渡すインターフェース
    interface CostValueListener {
        fun costValue(view: View, paraCost: Int)
    }

    //paraCostをDetailに渡すリスナー
    fun getCostValueListener(costListener: CostValueListener) {
        this.costListener = costListener
    }
}

