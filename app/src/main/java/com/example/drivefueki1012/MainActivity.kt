package com.example.drivefueki1012

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

        //Spinner
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val spinner = parent as? Spinner
            val item = spinner?.selectedItem as? String



        }



    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
        //入力完了ボタン（inputComp）を遷移用ボタンとして設定
        val button: Button=findViewById<Button>(R.id.inputComp)

        button.setOnClickListener{//押したら

            //Intent作成 MainActivityの金額入力からフォルダー詳細に遷移
            val intent = Intent(this@MainActivity,CameraActivity::class.java)
            startActivity(intent)
        }

        val camerabutton: Button=findViewById<Button>(R.id.camera)

        camerabutton.setOnClickListener{

            val intentcamera=Intent(this@MainActivity,CameraXActivity::class.java)
            startActivity(intentcamera)
        }

    }
}
