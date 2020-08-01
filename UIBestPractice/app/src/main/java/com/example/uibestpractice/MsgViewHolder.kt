package com.example.uibestpractice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//定义一个密封类
//继承自RecyclerView.ViewHolder
sealed class MsgViewHolder(view: View): RecyclerView.ViewHolder(view)

//密封类 有2个已知子类
class LeftViewHolder(view: View):MsgViewHolder(view){
    val leftMsg:TextView = view.findViewById(R.id.leftMsg)
}

class RightViewHolder(view: View):MsgViewHolder(view){
    val rightMsg:TextView = view.findViewById(R.id.rightMsg)
}