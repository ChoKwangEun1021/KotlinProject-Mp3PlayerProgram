package com.example.mp3playerondbpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mp3playerondbpro.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    companion object{
        private val TIME: Long = 1000
    }
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //일정 시간 지연이후 실행하기 위한것
        Handler(Looper.getMainLooper()).postDelayed({

            //일정 시간이 지나면 MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
            // 이동한 다음 사용안함으로 finish 처리
            finish()
        }, TIME)
    }
}