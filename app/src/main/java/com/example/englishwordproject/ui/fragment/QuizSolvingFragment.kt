package com.example.englishwordproject.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuizSolvingFragment(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2   //탭은 2개다.
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QuizFragment()
            1 -> ResultFragment()
            else -> QuizFragment()   // default는 퀴즈풀기탭
        }
    }


}