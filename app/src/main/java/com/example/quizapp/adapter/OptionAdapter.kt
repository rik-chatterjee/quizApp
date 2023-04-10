package com.example.quizapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.quizapp.R
import com.example.quizapp.models.Question

class OptionAdapter(val context: Context, val question: Question) : Adapter<OptionAdapter.OptionViewHolder>(){

    val optionList = listOf(question.option1,question.option2,question.option3, question.option4)

    inner class OptionViewHolder(itemView : View): ViewHolder(itemView){
        val optionView = itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionView.text = optionList[position]
        holder.itemView.setOnClickListener {
            question.userAnswer = optionList[position]
            notifyDataSetChanged()
        }
        if(question.userAnswer == optionList[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }

    }

    override fun getItemCount(): Int {
        return optionList.size
    }
}