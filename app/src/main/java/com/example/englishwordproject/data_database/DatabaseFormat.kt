package com.example.englishwordproject.data_database

import java.io.Serializable

data class DatabaseFormat(var DB_Name:String, var DB_VERSION:Int, var TABLE_NAME:String, var WORD:String, var MEAN:String, var TEMP:String):Serializable
//데이터베이스 파일 내부에 테이블이 2개라 따로 데이터 클래스를 생성
