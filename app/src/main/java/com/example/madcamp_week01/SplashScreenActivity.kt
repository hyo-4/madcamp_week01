package com.example.madcamp_week01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //이 메소드는 타이머가 끝나면 실행됩니다.
        Handler().postDelayed(Runnable {
            // 앱의 main activity로 넘어가기
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            // 현재 액티비티 닫기
            finish()
        }, 3000)
    }
}