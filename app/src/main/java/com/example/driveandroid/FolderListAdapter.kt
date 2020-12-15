/*
* FolderListAdapter
* 更新者：笛木
* 更新日：2020年12月14日
* 内容：deleteリスナー組み込み interface設定 deleteに送るidをdeleteId、detailに送るidをdetailIdとしてfolderList[position].folderIdを代入、
* リスト更新処理用にpositionもdeleteリスナーに送る
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

    // リスナー格納変数設定
    lateinit var listener: OnItemClickListener

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
            //ひとまずログをだしておく
            Log.d("folderIdの中身をみておく", "${folderList[position].folderId}")
            //ゴミ箱imageクリック時の処理
            delete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val deleteId = folderList[position].folderId //positionのfolderIdを取得
                    Log.d("deleteするidの値", "$deleteId")
                    listener.onItemClickListener(view, deleteId, position)//FolderListのdeleteに送る
                }
            })

            itemView.setOnClickListener(object : View.OnClickListener {
                //クリック時の処理
                override fun onClick(v: View) {
                    val detailId = folderList[position].folderId //positionのfolderIdを取得
                    Log.d("detailに送るidの値", "$detailId")
                    //遷移先
                    val intent = Intent(v.context, FolderDetailActivity::class.java)
                    intent.putExtra(EXTRA_FOLDERID, detailId) //FolderDetailのselectに送る
                    v.context.startActivity(intent)
                }
            })
        }
    }

    //インターフェース作成 listenerにviewとdeleteId、positionを持たせる
    interface OnItemClickListener {
        fun onItemClickListener(view: View, deleteId: Int, position: Int)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}