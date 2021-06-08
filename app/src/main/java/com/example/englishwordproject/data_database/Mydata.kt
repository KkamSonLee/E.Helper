package com.example.englishwordproject.data_database

import java.io.Serializable

data class Mydata(var word:String, var mean:String, var isKnown:Int):Serializable
//메인 단어들의 뜻, 의미, 즐겨찾기 여부
//문제 풀이 단어들의 뜻, 의미, 풀이결과
//를 담고있습니다.