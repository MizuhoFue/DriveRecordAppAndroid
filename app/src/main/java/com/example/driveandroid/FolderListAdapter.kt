/*
* FolderListAdapter
* 更新者：笛木
* 更新日：2020年12月7日
* 内容：BindViewHolder内、folderList[position].toString()のtoString()を削除 日付をStringにしたため
*
* */
package com.example.driveandroid

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.recyclerview_item.view.*

//日付配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderListAdapter(
    private var folderList: ArrayList<FolderInfo>
) : RecyclerView.Adapter<FolderListAdapter.CustomViewHolder>() {
    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val date = view.date
        val title = view.title
        val delete = view.delete
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
        with(holder) {
            date.text =
                folderList[position].date //String型　すでにスラッシュ入り
            title.text = folderList[position].title
            //星野さんがdeleteのリスナー書いてる
            itemView.setOnClickListener(object : View.OnClickListener {
                //クリック時の処理 TODO select処理実装
                override fun onClick(v: View) {
                    Log.d("folderidの値", "${position + 1}")
                    //遷移先
                    var intent = Intent(v.context, FolderDetailActivity::class.java)
                    intent.putExtra(EXTRA_FOLDERID, position + 1) //position+1でfolderidだった
                    v.context.startActivity(intent)
                }
            })
        }
    }
}