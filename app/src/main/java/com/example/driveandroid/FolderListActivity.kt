package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_folder_list.*

class FolderListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_folder_list)

//        //recyclerviewの中身
//        viewManager = LinearLayoutManager(this)
//        //viewAdapter = MyAdapter(myDataset)
//
//        recyclerView.apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//
//        }

        returnMap.setOnClickListener {
            val intent = Intent(this@FolderListActivity, MapsActivity::class.java)
            startActivity(intent)
        }

        settings.setOnClickListener {
            val intent = Intent(this@FolderListActivity, SupportActivity::class.java)
            startActivity(intent)
        }

        addFolder.setOnClickListener {
            val intent = Intent(this@FolderListActivity, FolderCreateActivity::class.java)
            startActivity(intent)
        }
    }
}