package com.example.englishwordproject.data_database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyWordListDBHelper(context: Context?) :
    SQLiteOpenHelper(context, "mydb.db", null, 1) {

    companion object {
        val myMainDatabaseFormat = DatabaseFormat(
            "mydb.db", 1, "myList",
            "word", "mean", "known"
        )   //메인 데이터용 myList 테이블

        val myResultDatabaseFormat = DatabaseFormat(
            "mydb.db", 1, "myResult",
            "word", "mean", "result"
        )   //문제 풀이 결과용 myResult 테이블 known과 result의 차이가 있음
    }

    fun getAllRecord(temp: Int): ArrayList<Mydata> {
        var word = ""
        if (temp == 0) {
            word = "select * from ${myMainDatabaseFormat.TABLE_NAME};"  //myList 테이블
        } else {
            word = "select * from ${myResultDatabaseFormat.TABLE_NAME};"   //myResult 테이블
        }
        val db = readableDatabase   // 받아올거라 read권한
        val cursor = db.rawQuery(word, null)  //다수의 데이터가 오기 때문에 커서로 받음.
        var myDataList = ArrayList<Mydata>()
        //타이틀 만들기
        if (cursor.count == 0) {   // 없으면 서칭 종료
            cursor.close()
            db.close()
            return ArrayList<Mydata>()   //null 에러 방지를 위해 빈 ArrayList 반환
        } else {
            cursor.moveToFirst()
            do {
                myDataList.add(
                    Mydata(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2).toInt()
                    )   //word(0), mean(1), known or result(2) 순서로 받아온다.
                )
            } while (cursor.moveToNext())
            cursor.close()
            db.close()
            if(myDataList.size==0){
                return ArrayList<Mydata>()  //이후에 데이터가 없을 수도 있으므로 예외처리
            }else{
                return myDataList   //결과 반환
            }
        }
    }
    fun getItemCount(result:Int):Int{//문제풀이 Result 탭에서 틀린것과 맞은 아이템들의 갯수를 판별하기 위해 만들었음
        var sql = "select word from ${myResultDatabaseFormat.TABLE_NAME} where result=$result"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)
        return cursor.count
    }

    fun insertData(data: Mydata, iscorrect:Int): Boolean {  //문제 풀이 결과 아이템 전달 및 삽입 메소드 결과 iscorrect까지 받아온다.
        val values = ContentValues()     //myResult에서만 쓰임
        values.put(myResultDatabaseFormat.WORD, data.word) //받은 데이터는 원래 myList테이블의 데이터였다. 따라서 iscorrect까지 필요함
        values.put(myResultDatabaseFormat.MEAN, data.mean)
        values.put(myResultDatabaseFormat.TEMP, iscorrect) //known과는 상이한 데이터기 때문
        val db = writableDatabase
        val flag = db.insert(myResultDatabaseFormat.TABLE_NAME, null, values) > 0  //성공 결과 flag에 반횐
        db.close()  // 꼭 닫아줘야함.
        return flag  //flag값 return
    }
    fun dropTable(temp: Int) {  //초기 SQLite 작업을 위해 생성한 메소드 원하는 테이블 drop
        var drop_table = ""
        if (temp == 0) {
            drop_table = "drop table if exists ${myMainDatabaseFormat.TABLE_NAME};"
        } else {
            drop_table = "drop table if exists ${myResultDatabaseFormat.TABLE_NAME};"
        }
        val db = writableDatabase
        db!!.execSQL(drop_table)
    }

    fun updateProduct(data: Mydata, is_known: Int): Boolean {  //일일 단어장에서 하트를 눌렀을 때 known값 업데이트 메소드 myList에서만 쓰임
        val word = data.word
        val mean = data.mean
        val strsql =   //혹시나 중복 단어가 있을수도 있어서 and 구문을 추가했다. word를 기본키 지정을 하지 않았기 때문, 초기 텍스트 파일에 중복 영단어가 너무 많았음.
            "select * from ${myMainDatabaseFormat.TABLE_NAME} where ${myMainDatabaseFormat.WORD}='$word' and ${myMainDatabaseFormat.MEAN}='$mean'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0   //받아온 데이터가 있을 때에만 if문 실행
        if (flag) {
            cursor.moveToFirst()
            val values = ContentValues()
            values.put("word", data.word)
            values.put("mean", data.mean)
            values.put("known", is_known)      //즐겨찾기를 할지, 해제할지에 대한 값 삽입
            db.update(
                myMainDatabaseFormat.TABLE_NAME,
                values,
                "${myMainDatabaseFormat.WORD}=?",   //update문
                arrayOf(word)
            )
        }
        cursor.close()
        db.close()
        return flag
    }

    fun getLikeRecoed(): ArrayList<Mydata>? {   //즐겨찾기를 한 데이터만 반환
        val isknown = "select * from ${myMainDatabaseFormat.TABLE_NAME} where known = 1;"
        val db = readableDatabase
        val like_cursor = db.rawQuery(isknown, null)  //known 값이 1이면 즐겨찾기(하트)를 한 데이터이다.
        var myDataList = ArrayList<Mydata>()
        //타이틀 만들기
        if (like_cursor.count == 0) {
            like_cursor.close()
            db.close()
            return ArrayList<Mydata>()
        } else {
            like_cursor.moveToFirst()
            do {   //삽입 로직은 이전과 같다.
                var word = like_cursor.getString(0)
                var mean = like_cursor.getString(1)
                var isknown = like_cursor.getString(2)
                myDataList.add(Mydata(word, mean, isknown.toInt()))
            } while (like_cursor.moveToNext())
            like_cursor.close()
            db.close()
            return myDataList
        }

    }

    fun delete(temp: Int) {   //SQLite 초기 작업을 위해 생성, 내부 아이템들만 delete해주는 질의문
        var strsql = ""
        if (temp == 0) {
            strsql = "delete from ${myMainDatabaseFormat.TABLE_NAME}"
        } else {
            strsql = "delete from ${myResultDatabaseFormat.TABLE_NAME}"
        }
        val db = writableDatabase
        db.execSQL(strsql)
    }

    fun deleteProduct(word: String, temp: Int) {   // 나의 단어장에서 제외시킬 때 요청되는 메소드 초기 작업에만 사용됐다. 지금은 update로 바꿈
        var strsql = ""
        val db = writableDatabase
        var flag = false
        if (temp == 0) {
            strsql = "select * from ${myMainDatabaseFormat.TABLE_NAME} where word='$word'"  //제외시킬 단어 select
            val cursor = db.rawQuery(strsql, null)
            flag = cursor.count != 0
            if (flag) {
                cursor.moveToFirst()
                db.delete(
                    myMainDatabaseFormat.TABLE_NAME,
                    "${myMainDatabaseFormat.WORD}=?",
                    arrayOf(word)
                )
            }
        } else {
            strsql = "select * from ${myResultDatabaseFormat.TABLE_NAME} where word='$word'"
            val cursor = db.rawQuery(strsql, null)
            flag = cursor.count != 0
            if (flag) {
                cursor.moveToFirst()
                db.delete(
                    myResultDatabaseFormat.TABLE_NAME,
                    "${myResultDatabaseFormat.WORD}=?",
                    arrayOf(word)
                )
            }
            cursor.close()
            db.close()
        }
    }

    fun setAllRecord(data: ArrayList<Mydata>): Boolean {    //초기 작업으로 사용했던 것. words 텍스트 파일로 읽어온 데이터를 삽입하는 메소드
        val values = ContentValues()
        val db = writableDatabase
        data.forEach {
            values.put("word", it.word)
            values.put("mean", it.mean)
            values.put("known", 0)
            val flag = db.insert(myMainDatabaseFormat.TABLE_NAME, null, values) > 0
            if (!flag) {
                db.close()
                return flag
            }
        }
        db.close()  // 꼭 닫아줘야함.
        return true
    }


    override fun onCreate(db: SQLiteDatabase?) {   //2개의 테이블을 생성해주는 로직을 담고 있다.
        Log.i("create", "create!!!")

        val create_table = "create table if not exists ${myMainDatabaseFormat.TABLE_NAME}(" +
                "${myMainDatabaseFormat.WORD} text, " +
                "${myMainDatabaseFormat.MEAN} text, " +
                "${myMainDatabaseFormat.TEMP} Integer);"
        db?.execSQL(create_table)
        val createResult_table =
            "create table if not exists ${myResultDatabaseFormat.TABLE_NAME}(" +
                    "${myResultDatabaseFormat.WORD} text, " +
                    "${myResultDatabaseFormat.MEAN} text, " +
                    "${myResultDatabaseFormat.TEMP} Integer);"
        db?.execSQL(createResult_table)


    }//처음 생성될 때

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists ${myMainDatabaseFormat.TABLE_NAME};"
        val drop_result_table = "drop table if exists ${myResultDatabaseFormat.TABLE_NAME};"
        db!!.execSQL(drop_table)
        db!!.execSQL(drop_result_table)
        onCreate(db)//삭제 후 재생성
    }

}