package com.example.englishwordproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordproject.data_database.MyWordListDBHelper
import com.example.englishwordproject.data_database.Mydata
import com.example.englishwordproject.databinding.ActivityMyWordBookBinding
import java.util.*

class MyWordBookActivity : AppCompatActivity() {
    lateinit var binding:ActivityMyWordBookBinding
    lateinit var mydata: ArrayList<Mydata>
    lateinit var adapter: MyWordBookAdapter
    var myDBHelper: MyWordListDBHelper?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWordBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        binding.mywordBack.setOnClickListener {
            finish()
        }
        myDBHelper = MyWordListDBHelper(this)
        mydata = myDBHelper!!.getLikeRecoed()!!   //즐겨찾기(하트)한 데이터만 받아옴
        adapter = MyWordBookAdapter(mydata)       //어뎁터 생성 후 삽입
        adapter.itemOnClickListener = object : MyWordBookAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: RecyclerView.ViewHolder,
                view: View,
                data: Mydata,
                position: Int
            ) {
                myDBHelper!!.updateProduct(data, 0)   // known을 0으로(즐겨찾기 해제) 바꿔주는 작업
                Toast.makeText(applicationContext, mydata[position].word+"가 삭제 되었습니다", Toast.LENGTH_SHORT).show()
                mydata.removeAt(position)     //해당 아이템도 삭제해준다.
                adapter.notifyDataSetChanged()
            }

        }
        binding.myRecyclerView.adapter = adapter   //recyclerview 어뎁터와 연결

    }

    override fun onDestroy() {
        super.onDestroy()
        myDBHelper = null
    }
}