package com.example.seoulpublicswimmingpool.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.seoulpublicswimmingpool.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }
    private fun initView(){
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, 4000)
    }
}