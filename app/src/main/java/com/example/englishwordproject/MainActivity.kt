package com.example.englishwordproject

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishwordproject.broad_and_prefs.App
import com.example.englishwordproject.data_database.MyWordListDBHelper
import com.example.englishwordproject.data_database.Mydata
import com.example.englishwordproject.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var tts: TextToSpeech
    var myDataArray: ArrayList<Mydata> = ArrayList()
    var index: Int = 0

    lateinit var myDBHelper: MyWordListDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }


    private fun init() {
        index = App.prefs.myIndex!!   //shared preference 받는 곳
        myDBHelper = MyWordListDBHelper(this)
        initTTS()
        /*val scan = Scanner(resources.openRawResource(R.raw.words))
        readData(scan)
        myDBHelper.setAllRecord(myDataArray)*/

        myDataArray = myDBHelper.getAllRecord(0)!!
        binding.word.text = myDataArray[index].word.toUpperCase()
        binding.meaning.text = myDataArray[index].mean
        //데이터베이스 데이터를 받아와서 텍스트뷰에 삽입하는 과정

        if (myDataArray[index].isKnown == 0) {
            binding.heartImage.setImageResource(R.drawable.btn_like_selected)
        } else {
            binding.heartImage.setImageResource(R.drawable.btn_like_default)
        }
        //현재 (INDEX)포커스된 즐겨찾기 여부를 통해 이미지로 변환

        binding.playbtn.setOnClickListener {
            tts.language = Locale.US
            tts.speak(binding.word.text, TextToSpeech.QUEUE_ADD, null, null)
        }//TTS 서비스 삽입
        binding.leftbtn.setOnClickListener {
            setWord(-1)
        }//이전 단어
        binding.rightbtn.setOnClickListener {
            setWord(1)
        }//다음 단어
        binding.okaybtn.setOnClickListener {
            finish()
        }//HomeActivity로 이동 혹은 잠금화면 나오기
        binding.heartImage.setOnClickListener { //즐겨찾기 로직
            if (myDataArray[index].isKnown == 0) {//빈 하트면 하트로 만들어주고 데이터 업데이트
                myDataArray[index].isKnown = 1
                binding.heartImage.setImageResource(R.drawable.btn_like_selected)
                myDBHelper.updateProduct(myDataArray[index], 1)
            } else {  //이미 즐겨찾기updateProduct에 있으면 즐겨찾기에서 해제 후 업데이트
                myDataArray[index].isKnown = 0
                binding.heartImage.setImageResource(R.drawable.btn_like_default)
                myDBHelper.updateProduct(myDataArray[index], 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setWord(0)  //나의 단어장에서 지웠을 때 하트가 사라져 있어야함.
        //하트가 포커스인채로 나갔다가 들어오면 다시 세팅해주는 부분
    }
    private fun readData(scan: Scanner) {
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            myDataArray.add(Mydata(word, meaning, 0))
        }
        scan.close()
    }   //텍스트 파일 읽어오는 데에 사용하였음. words.txt 외부자료

    private fun setWord(way: Int) {
        //index에서 이동할 때 0번째, array.size범위 내부에서만 바뀌게 해주는 부분
        if (index + way >= 0 || index + way <= myDataArray.size) {
            index += way
            App.prefs.myIndex = index
        } else {  //범위를 초과하려하면 토스트 메세지 호출
            Toast.makeText(this, "끝입니다.", Toast.LENGTH_SHORT).show()
        }
        binding.word.text = myDataArray[index].word.toUpperCase()
        binding.meaning.text = myDataArray[index].mean
        if (myDataArray[index].isKnown == 1) {  //즐겨찾기 이미지 생성 1이면 하트 0이면 빈 하트
            binding.heartImage.setImageResource(R.drawable.btn_like_selected)
        } else {
            binding.heartImage.setImageResource(R.drawable.btn_like_default)
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {

        })
    }

}