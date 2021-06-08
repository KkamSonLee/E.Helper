package com.example.englishwordproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.englishwordproject.databinding.QuizSolvingActivityBinding
import com.example.englishwordproject.ui.fragment.QuizSolvingFragment
import com.google.android.material.tabs.TabLayoutMediator

class QuizSolvingActivity : AppCompatActivity() {

    lateinit var binding: QuizSolvingActivityBinding
    var textarr = listOf<String>("test", "result")    //탭 text
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QuizSolvingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.viewPager.adapter = QuizSolvingFragment(this)  //뷰페이저 FragmentStateAdapter 어뎁터 생성 후 삽입

        TabLayoutMediator(binding.myTabIconview, binding.viewPager) { tab, position ->
            tab.text = textarr[position]
        }.attach() //꼭 attach
        //뷰 페이저와 탭 레이아웃을 연결하는 코드
    }

}