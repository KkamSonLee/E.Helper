package com.example.englishwordproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.englishwordproject.data_database.MyWordListDBHelper
import com.example.englishwordproject.data_database.Mydata
import com.example.englishwordproject.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    lateinit var binding:FragmentResultBinding
    lateinit var myDataArray: ArrayList<Mydata>
    lateinit var adapter: MyResultAdapter
    var myResultDBHelper: MyWordListDBHelper?= null
    var isReset:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        adapter = MyResultAdapter(myDataArray)  //받아온 데이터로 어뎁터 생성 후 삽입
        binding.resultListView.adapter = adapter  //recyclerview에 삽입
        return binding!!.root
    }

    private fun init() {
        binding = FragmentResultBinding.inflate(layoutInflater)
        myResultDBHelper = MyWordListDBHelper(context)
        myDataArray = myResultDBHelper!!.getAllRecord(1) //myResult 테이블의 데이터 받아온 후 삽입
        var o =  myResultDBHelper!!.getItemCount(1)  //맞은 아이템 갯수 반환
        var x =  myResultDBHelper!!.getItemCount(0)  //틀린 아이템 갯수 반환
        binding.oResult.text = o.toString()                //각 텍스트에 갯수 삽입
        binding.xResult.text = x.toString()
        binding.totalResult.text = "총 "+(o+x).toString()  //총 갯수 계산 후 삽입
    }
    fun resetData() {  //문제 풀기 이후에 결과가 삽입 되어야함. 하지만 탭이동시 onCreate가 호출되지 않기 때문에 새로운 메소드 생성
        myDataArray.clear()    //데이터를 다시 받아올 것이기 때문에 clear
        myDataArray.addAll(myResultDBHelper!!.getAllRecord(1))  //다시 myResult 테이블 데이터 삽입
        var o =  myResultDBHelper!!.getItemCount(1)  //init()과 같음
        var x =  myResultDBHelper!!.getItemCount(0)
        binding.oResult.text = o.toString()
        binding.xResult.text = x.toString()
        binding.totalResult.text = "총 "+(o+x).toString()
        adapter.notifyDataSetChanged()
    }
    // result탭을 갔다가 test탭에서 문제를 푼 후 결과를 보기위해 result에 갔을 때의
    // 생명주기가 resume() -> pause() -> resume()인 것을 힌트로 작업하였음.
    // isReset을 초기 false로 선언한 후 반전 값을 삽입하면 항상 2번째 resume()에만 true임
    // init과 resetData의 중복 실행을 막음
    override fun onPause() {
        super.onPause()
        isReset = !isReset
    }
    override fun onResume() {
        super.onResume()
        isReset = !isReset
        if(isReset){
            resetData()  //true이면 resetData로 바뀐 결과가 있다면 그 데이터로 삽입
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        myResultDBHelper = null
    }
}