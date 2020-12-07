package com.example.driveandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_to_use.view.*

//金額配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderDetailAdapter(
    private val folderDetail: ArrayList<ItemToUse>
) :

    RecyclerView.Adapter<FolderDetailAdapter.CustomViewHolder>() {
    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val paraName = view.paraName
        val paracostView = view.paracostView
//      val perPerson_costView = view.perParson_costView
        val payerView = view.payerView
        val trashbox = view.trashBox
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
        return this.folderDetail.size   //サイズ足りる？titleが基準？
    }

    // ViewHolderに表示するテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        with(holder) {
            paraName.text = folderDetail[position].paraNames
            paracostView.text = folderDetail[position].paraCosts.toString()
//            perPerson_costView.text = folderDetail[position].paraCosts.toString()
            payerView.text = folderDetail[position].payers
            trashbox.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //TODO削除処理
//                    // BuilderからAlertDialogを作成
//                    val dialog = AlertDialog.Builder(this)
//                        .setTitle(R.string.finish_message) // タイトル
//                        .setPositiveButton(R.string.yes) { dialog, which -> // OK
//
//                        }
//                        .setNegativeButton(R.string.no) { dialog, which -> //no
//
//                        }
//                        .create()
//                    // AlertDialogを表示
//                    dialog.show()
                }
            })
        }
    }
}

