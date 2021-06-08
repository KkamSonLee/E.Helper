package com.example.englishwordproject.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordproject.R
import com.example.englishwordproject.data_database.Mydata
import com.example.englishwordproject.databinding.ResultRowBinding

class MyResultAdapter(var values: ArrayList<Mydata>) :
    RecyclerView.Adapter<MyResultAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ResultRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.myWord
        val detail: TextView = binding.myMean
        var result: ImageView = binding.result
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyResultAdapter.ViewHolder {
        val view = ResultRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyResultAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.word
        holder.detail.text = item.mean
        if(item.isKnown==0){    //맞은건 작은 동그라미, 틀린건 작은 X로 이미지를 정해준다.
            holder.result.setImageResource(R.drawable.bad)
        }else{
            holder.result.setImageResource(R.drawable.good)
        }
    }

    override fun getItemCount(): Int = values.size


}