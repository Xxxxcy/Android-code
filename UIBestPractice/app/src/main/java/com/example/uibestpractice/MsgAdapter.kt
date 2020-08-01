package com.example.uibestpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.msg_left_item.view.*
import kotlinx.android.synthetic.main.msg_right_item.view.*

//class MsgAdapter(val msgList: List<Msg>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
class MsgAdapter(val msgList: List<Msg>) :RecyclerView.Adapter<MsgViewHolder>(){

    inner class LeftViewHolder(view:View):RecyclerView.ViewHolder(view){
        val leftMsg: TextView = view.findViewById(R.id.leftMsg)
    }

    inner class RightViewHolder(view:View):RecyclerView.ViewHolder(view){
        val rightMsg: TextView = view.findViewById(R.id.rightMsg)
    }

    //获取消息的类型
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    //通过不同的类型来创建不同的ViewHolder
    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder = if (viewType ==Msg.TYPE_RECEIVED){
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item,parent,false)
        LeftViewHolder(view)
    }else{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item,parent,false)
        RightViewHolder(view)
    }

     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == Msg.TYPE_RECEIVED) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent, false)
        com.example.uibestpractice.LeftViewHolder(view)
    } else {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false)
        com.example.uibestpractice.RightViewHolder(view)
    }

    /*
    首先onCreateViewHolder的目的是创建viewHolder，viewHolder作为recyclerView缓存管理的对象，是可以在列表中复用的。
    而onBindeViewHolder方法的调用时机是item出现（或将要出现）在屏幕上时，这时需要向传入的viewHolder中填充数据等，这个viewHolder可能是item不可见后回收复用的，在bind此类viewHolder的时候是不会重复创建的。
     */
    //override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        when(holder){
            //is LeftViewHolder -> holder.leftMsg.text = msg.content
            //is RightViewHolder -> holder.rightMsg.text = msg.content
            //满足编译器检测 否则会认为确实条件分支
            //else -> throw IllegalAccessException()

            //密封类 在编译过程中 会自动检测有哪些子类  并对其全部处理 不会出现漏写分支条件的情况
            //使用密封类  防止多余else分支
            is com.example.uibestpractice.LeftViewHolder -> holder.leftMsg.text = msg.content
            is com.example.uibestpractice.RightViewHolder -> holder.rightMsg.text = msg.content
    }
}

    override fun getItemCount() = msgList.size
}
