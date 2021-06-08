package com.example.englishwordproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordproject.data_database.Mydata

class MyWordBookAdapter(var items:ArrayList<Mydata>):RecyclerView.Adapter<MyWordBookAdapter.MyViewHolder>(){
    var itemOnClickListener:OnItemClickListener?=null


    interface OnItemClickListener{
        fun OnItemClick(holder: RecyclerView.ViewHolder, view:View, data: Mydata, position:Int)
    }

    inner class MyViewHolder(myView: View) : RecyclerView.ViewHolder(myView) {
        val word: TextView = itemView.findViewById(R.id.my_word)
        val mean: TextView = itemView.findViewById(R.id.my_mean)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        init {
            delete.setOnClickListener {
                itemOnClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWordBookAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyWordBookAdapter.MyViewHolder, position: Int) {
        holder.word.text = items[position].word
        holder.mean.text = items[position].mean
    }

    override fun getItemCount(): Int {
        return items.size
    }

}