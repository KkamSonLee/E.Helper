package com.example.englishwordproject

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.englishwordproject.broad_and_prefs.Mybroadcast
import com.example.englishwordproject.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var br:BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBroad()
        init()
    }


    private fun initBroad() {  // 브로드캐스트 설정
        br = Mybroadcast()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(br, filter)   // SCREEN OFF일 때 받아온다.
    }

    private fun init() {    //각 액티비티 전환
        binding.lockbtn.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }
        binding.mywordbtn.setOnClickListener {

            val intent = Intent(this, MyWordBookActivity ::class.java)
            startActivity(intent)
        }
        binding.solvingbtn.setOnClickListener {

            val intent = Intent(this, QuizSolvingActivity ::class.java)
            startActivity(intent)
        }

    }
}