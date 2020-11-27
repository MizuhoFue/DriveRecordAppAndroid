package com.example.driveandroid

//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.TextView
//import androidx.appcompat.widget.AppCompatImageButton
//import androidx.appcompat.widget.AppCompatImageView
//import androidx.appcompat.widget.AppCompatTextView
//import kotlinx.android.synthetic.main.activity_folder_detail.view.*
//import kotlinx.android.synthetic.main.item_to_use.view.*
//
//class FolderDetailAdapter (val context: Context, val FolderDetail: ArrayList<User>) : BaseAdapter() {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_folder_detail, null)
//
//        val folderDetail = FolderDetail[position]
//
//        view.paraName.text = folderDetail.paraName
//        view.cost_name.text = folderDetail.cost_name
//        view.paracostView.text = folderDetail.paracostView
//        view.total_yen.text = folderDetail.total_yen
//        view.perPerson.text = folderDetail.perPerson
//        view.perPerson_costView.text = folderDetail.perPerson_costView
//        view.yen.text = folderDetail.yen
//        view.payer_name.text = folderDetail.payer_name
//        view.payerView.text = folderDetail.payerView
//        TrashBox.text = folderDetail.trashbox
//
//        return view
//    }
//
//    override fun getItem(position: Int): Any {
//        return FolderDetail[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getCount(): Int {
//        return FolderDetail.size
//    }
//
//}

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_to_use.view.*

//金額配列とタイトル配列を表示+Delete処理のゴミ箱imageView
class FolderDetailAdapter(private val folderDetail: Array<String>) :

    RecyclerView.Adapter<FolderDetailAdapter.CustomViewHolder>() {

    // ViewHolderクラス
    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val paraName = view.paraName
        val cost_name = view.cost_name
        val paracostView = view.paracostView
        val total_yen = view.total_yen
        val perPerson = view.perParson
        val perPerson_costView = view.perParson_costView
        val yen = view.yen
        val payer_name = view.payer_name
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
            paraName.text = folderDetail[position].toString()
            cost_name.text = folderDetail[position].toString()
            paracostView.text = folderDetail[position].toString()
            total_yen.text = folderDetail[position].toString()
            perPerson.text = folderDetail[position].toString()
            perPerson_costView.text = folderDetail[position].toString()
            yen.text = folderDetail[position].toString()
            payer_name.text = folderDetail[position].toString()
            payerView.text = folderDetail[position].toString()
            trashbox.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    //削除処理
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

