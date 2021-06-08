package com.example.englishwordproject.broad_and_prefs

import android.content.Context
import android.content.SharedPreferences

class MyIndexSaveClass(context: Context) {
    private val prefsFilename = "prefs" //파일 이름
    private val prefsKeyIndex = "myIndex"
    private val prefs:SharedPreferences = context.getSharedPreferences(prefsFilename, 0)  //저장된 값 불러오기
    var myIndex:Int?  //이 변수로 get, set.
    get() = prefs.getInt(prefsKeyIndex, 0) //반환
    set(value) = prefs.edit().putInt(prefsKeyIndex, value!!).apply() //저장
}