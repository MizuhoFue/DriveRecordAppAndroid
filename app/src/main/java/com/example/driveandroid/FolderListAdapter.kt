/*
* FolderListAdapter
* 更新者：笛木
* 更新日：2020年11月20日
* 内容：日付、タイトルの表示処理
* */
package com.example.driveandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

//日付配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderListAdapter(
    private var dateList: ArrayList<Int>,
    private var titleList: ArrayList<String>
) : RecyclerView.Adapter<FolderListAdapter.CustomViewHolder>() {

    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var date = view.date
        var title = view.title
        var delete = view.delete
    }
    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return CustomViewHolder(item)
    }
    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return titleList.size   //サイズ足りる？titleが基準？
    }
    // ViewHolderに表示するテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder) {
            date.text = dateList[position].toString() //positionつけたらコンパイルエラー、String型
            title.text = titleList[position]
            itemView.setOnClickListener(object : View.OnClickListener {
                //クリック時の処理 　セレクトして詳細を表示するにはここでfolderidを渡す必要あり putExtraしたい・・・
                override fun onClick(v: View) {
                    //ここにアイテムをクリックした際の挙動を記載
                    var context = v.context
                    context.startActivity(Intent(context, FolderDetailActivity::class.java))
                }
            })
        }
    }
}