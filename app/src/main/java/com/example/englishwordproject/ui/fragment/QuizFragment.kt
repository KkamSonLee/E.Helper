package com.example.englishwordproject.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.englishwordproject.R
import com.example.englishwordproject.data_database.MyWordListDBHelper
import com.example.englishwordproject.data_database.Mydata
import com.example.englishwordproject.databinding.FragmentQuizBinding
import kotlin.random.Random

class QuizFragment : Fragment() {
    lateinit var binding: FragmentQuizBinding
    lateinit var myDataArray: ArrayList<Mydata>

    var myDBHelper: MyWordListDBHelper? = null
    var answerPosi: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        myDataArray = ArrayList<Mydata>()
        myDBHelper = MyWordListDBHelper(context)
        myDataArray = myDBHelper!!.getAllRecord(0)
        Log.i("data size", myDataArray.size.toString())
        init()
        return binding.root
    }

    private fun init() {
        setWord()
        binding.apply {     // 버튼 4개의 대한 모든 이벤트 처리, 버튼 하나에 대해서만 봐도 된다.
            var backgroundColor =
                ContextCompat.getColor(requireContext(), R.color.solve_text_correct) // 답인 버튼 색을 바꿀 Color
            q1btn.setOnClickListener {
                if (q1btn.text.equals(myDataArray[answerPosi].mean)) {  //선택한 항목이 맞다면
                    q1btn.setBackgroundColor(backgroundColor)           //선택 버튼 파란색으로 변환
                    q1btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)) //글자색은 흰색으로 (원래는 파란색)
                    good.visibility = View.VISIBLE   //큰 동그라미 보여줌.
                    myDBHelper!!.insertData(myDataArray[answerPosi], 1)    //myResult 데이터에 삽입해준다. 맞았으니깐 result 1
                } else {
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.today_text_incorrect)  //틀린 부분 바꿔줄 Color 빨간색
                    q1btn.setBackgroundColor(backgroundColor)
                    q1btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)) //글자색은 흰색으로
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.solve_text_correct)
                    if (q2btn.text.equals(myDataArray[answerPosi].mean)) {   //답인 버튼을 찾아서 버튼은 파란색 글자는 흰색으로 바꿔주는 코드
                        q2btn.setBackgroundColor(backgroundColor)
                        q2btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q3btn.text.equals(myDataArray[answerPosi].mean)) {
                        q3btn.setBackgroundColor(backgroundColor)
                        q3btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q4btn.text.equals(myDataArray[answerPosi].mean)) {
                        q4btn.setBackgroundColor(backgroundColor)
                        q4btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                    bad.visibility = View.VISIBLE   //틀렸으니깐 큰 X가 UI에 띄워짐
                    myDBHelper!!.insertData(myDataArray[answerPosi], 0)   //myResult 데이터에 삽입해준다. 틀렸으니깐 result 0
                }
                delayFun()  //해당 UI변화를 일정 시간 유지해주면서 클릭 이벤트 막은 후 다음 단어
            }



            q2btn.setOnClickListener {
                if (q2btn.text.equals(myDataArray[answerPosi].mean)) {
                    q2btn.setBackgroundColor(backgroundColor)
                    q2btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    good.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 1)
                } else {
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.today_text_incorrect)
                    q2btn.setBackgroundColor(backgroundColor)
                    q2btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.solve_text_correct)
                    if (q1btn.text.equals(myDataArray[answerPosi].mean)) {
                        q1btn.setBackgroundColor(backgroundColor)
                        q1btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q3btn.text.equals(myDataArray[answerPosi].mean)) {
                        q3btn.setBackgroundColor(backgroundColor)
                        q3btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q4btn.text.equals(myDataArray[answerPosi].mean)) {
                        q4btn.setBackgroundColor(backgroundColor)
                        q4btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                    bad.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 0)
                }
                delayFun()
            }
            q3btn.setOnClickListener {
                if (q3btn.text.equals(myDataArray[answerPosi].mean)) {
                    q3btn.setBackgroundColor(backgroundColor)
                    q3btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    good.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 1)
                } else {
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.today_text_incorrect)
                    q3btn.setBackgroundColor(backgroundColor)
                    q3btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    ContextCompat.getColor(requireContext(), R.color.solve_text_correct)
                    if (q2btn.text.equals(myDataArray[answerPosi].mean)) {
                        q2btn.setBackgroundColor(backgroundColor)
                        q2btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q1btn.text.equals(myDataArray[answerPosi].mean)) {
                        q1btn.setBackgroundColor(backgroundColor)
                        q1btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q4btn.text.equals(myDataArray[answerPosi].mean)) {
                        q4btn.setBackgroundColor(backgroundColor)
                        q4btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                    bad.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 0)
                }
                delayFun()
            }
            q4btn.setOnClickListener {

                if (q4btn.text.equals(myDataArray[answerPosi].mean)) {
                    q4btn.setBackgroundColor(backgroundColor)
                    q4btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    good.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 1)
                } else {
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.today_text_incorrect)
                    q4btn.setBackgroundColor(backgroundColor)
                    q4btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    backgroundColor =
                        ContextCompat.getColor(requireContext(), R.color.solve_text_correct)
                    if (q2btn.text.equals(myDataArray[answerPosi].mean)) {
                        q2btn.setBackgroundColor(backgroundColor)
                        q2btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q3btn.text.equals(myDataArray[answerPosi].mean)) {
                        q3btn.setBackgroundColor(backgroundColor)
                        q3btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else if (q1btn.text.equals(myDataArray[answerPosi].mean)) {
                        q1btn.setBackgroundColor(backgroundColor)
                        q1btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                    bad.visibility = View.VISIBLE
                    myDBHelper!!.insertData(myDataArray[answerPosi], 0)
                }
                delayFun()
            }
        }
    }

    fun delayFun() {
        binding.q1btn.isClickable = false  //버튼 4개의 클릭이벤트 막아줌
        binding.q2btn.isClickable = false
        binding.q3btn.isClickable = false
        binding.q4btn.isClickable = false
        Handler().postDelayed({
            binding.q1btn.isClickable = true
            binding.q2btn.isClickable = true
            binding.q3btn.isClickable = true
            binding.q4btn.isClickable = true
            binding.bad.visibility = View.GONE
            binding.good.visibility = View.GONE
            setWord()
        }, 2000L)  //2초후 큰 동그라미 OR 큰 X 사라지게 한 후 setWord()호출
    }

    private fun setWord() {   // 다음 단어를 보여주게 하는 메소드
        var random = 0   //랜덤으로 문제를 보여주게 하였다.
        setColor()   //이전 이벤트로 바뀐 색 복구
        while (true) {
            random = Random.nextInt(myDataArray.size - 3)  // (랜덤값-3) ~ 랜덤값 까지의 값으로 텍스트를 쓸거라 범위 체크해준다.
            if ((random - 3) >= 0) {
                break  // 범위안에 들어야 break
            }
        }
        answerPosi = (random - Random.nextInt(4)) //답은 (랜덤값-3) ~ 랜덤값 사이에서 랜덤하게 뽑아준다.
        binding.q1text.text = myDataArray[answerPosi].word.toUpperCase()
        binding.q1btn.text = myDataArray[random].mean
        binding.q2btn.text = myDataArray[random - 1].mean
        binding.q3btn.text = myDataArray[random - 2].mean
        binding.q4btn.text = myDataArray[random - 3].mean
    }

    private fun setColor() {  //문제 결과 이벤트 후 복구해줄 메소드


        var backgroundColor =
            ContextCompat.getColor(requireContext(), R.color.white)   //버튼색 (흰색)으로 바꿔줌
        var textColor =
            ContextCompat.getColor(requireContext(), R.color.solveview_text)   //글자식 (파란색)으로 바꿔줌
        binding.q1btn.setBackgroundColor(backgroundColor)
        binding.q2btn.setBackgroundColor(backgroundColor)
        binding.q3btn.setBackgroundColor(backgroundColor)
        binding.q4btn.setBackgroundColor(backgroundColor)
        binding.q1btn.setTextColor(textColor)
        binding.q2btn.setTextColor(textColor)
        binding.q3btn.setTextColor(textColor)
        binding.q4btn.setTextColor(textColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myDBHelper = null
    }
}