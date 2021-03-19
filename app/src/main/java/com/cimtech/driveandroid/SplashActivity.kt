package com.cimtech.driveandroid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val handler = Handler()
    private val runnable = Runnable {

        // 今はMoneyInsertActivityに遷移させる
        val intent = Intent(this@SplashActivity, FolderListActivity::class.java)
        startActivity(intent)
        // ここでfinish()を呼ばないとMainActivityでAndroidの戻るボタンを押すとスプラッシュ画面に戻ってしまう
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // スプラッシュ表示1000ms(1秒)後にrunnableを呼んでMeinActivityへ遷移させる
        //iOSと合わせて3秒にする
        handler.postDelayed(runnable, 3000)
    }

    override fun onStop() {
        super.onStop()
        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        // これがないとアプリを消した後にまた表示される
        handler.removeCallbacks(runnable)
    }
}