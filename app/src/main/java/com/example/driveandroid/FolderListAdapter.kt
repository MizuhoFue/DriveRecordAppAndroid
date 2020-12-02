/*
* FolderListAdapter
* 更新者：笛木
* 更新日：2020年11月25日
* 内容：ViewHolder内、Detailに遷移する際に仮folderid渡し
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
                folderList[position].date.toString() //positionつけたらコンパイルエラー、String型  TODO スラッシュ付けてフォーマットyyyy/MM/dd
            title.text = folderList[position].title
            //星野さんがdeleteのリスナー書いてる
            itemView.setOnClickListener(object : View.OnClickListener {
                //クリック時の処理 TODO select処理実装
                override fun onClick(v: View) {
                    Log.d("positionの値","${position}")
                    //遷移先
                    var intent = Intent(v.context, FolderDetailActivity::class.java)
                    intent.putExtra(EXTRA_FOLDERID, position) //position=folderid なのでこれで詳細に渡す 確認ずみ
                    v.context.startActivity(intent)
                }
            })
        }
    }
}