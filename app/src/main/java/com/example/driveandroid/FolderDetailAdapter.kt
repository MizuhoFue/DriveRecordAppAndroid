package com.example.driveandroid

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.driveandroid.Constants.Companion.EXTRA_FOLDERID
import kotlinx.android.synthetic.main.item_to_use.view.*

//金額配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderDetailAdapter(
    private val folderDetail: ArrayList<ItemToUse>
) : RecyclerView.Adapter<FolderDetailAdapter.CustomViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener

    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val paraName = view.paraName
        val paraCostView = view.para_cost_view
        val payerView = view.payerView
        val trashBox = view.trash_box
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
//            perPerson_costView.text = folderDetail[position].paraCosts.toString()
            payerView.text = folderDetail[position].payers
            //ひとまずログをだしておく
            Log.d("folderIdの中身をみておく", "${folderDetail[position].folderId}")
            //ゴミ箱クリック時
            trashBox.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val deleteId = folderDetail[position].folderId //positionのfolderIdを取得
                    val deleteNum = folderDetail[position].paraNum //削除する項目
                    Log.d("deleteするidの値", "$deleteId")
                    listener.onItemClickListener(view, deleteNum, position)//FolderDetailのdeleteに送る
                }
            })

            itemView.setOnClickListener(object : View.OnClickListener {
                //クリック時の処理
                override fun onClick(v: View) {
                    val detailId = folderDetail[position].folderId //positionのfolderIdを取得
                    Log.d("detailに送るidの値", "$detailId")
                    //遷移先
                    val intent = Intent(v.context, FolderDetailActivity::class.java)
                    intent.putExtra(EXTRA_FOLDERID, detailId) //FolderDetailのselectに送る
                    v.context.startActivity(intent)
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
}

