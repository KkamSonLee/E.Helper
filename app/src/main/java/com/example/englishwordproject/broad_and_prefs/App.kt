package com.example.englishwordproject.broad_and_prefs

import android.app.Application

class App: Application(){  //prefs를 저장
    companion object{
        lateinit var prefs : MyIndexSaveClass
    }

    override fun onCreate() {
        prefs = MyIndexSaveClass(applicationContext)  //prefs 객체를 생성 및 보관(lateinit)
        super.onCreate()
    }
}