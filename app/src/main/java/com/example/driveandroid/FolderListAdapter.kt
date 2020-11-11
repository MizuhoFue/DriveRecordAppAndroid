package com.example.driveandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class FolderListAdapter(private val folderList: Array<String>) :
    RecyclerView.Adapter<FolderListAdapter.CustomViewHolder>() {

    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dateList = view.date_list
        val titleList = view.title_list
        val deleteList = view.delete_list
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
        return folderList.size
    }

    // ViewHolderに表示するテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.title_list.text = folderList[position]
        holder.itemView.setOnClickListener(object : View.OnClickListener {

            //クリック時の処理
            override fun onClick(v: View) {
                //ここにアイテムをクリックした際の挙動を記載
                var context = v.context
                context.startActivity(Intent(context, FolderDetailActivity::class.java))
            }
        })
    }
}