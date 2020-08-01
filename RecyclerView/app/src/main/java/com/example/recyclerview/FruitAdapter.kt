package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FruitAdapter(val fruitList: List<Fruit>):
    //适配器继承自RecyclerView.Adapter 泛型为 FruitAdapter.ViewHolder
    RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    //定义内部类
    //将实例存放在viewholder中 减少findviewbyid重复使用
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fruitImage :ImageView = view.findViewById(R.id.fruitImage)
        val fruitName :TextView = view.findViewById(R.id.fruitName)
    }

    //创建ViewHolder 实例 并将布局传递进来 最后将实例返回
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_item, parent, false)

        val viewHolder = ViewHolder(view)

        //外层布局点击事件
        //点击TextView时候 未注册点击事件自动被最外层布局捕获
        viewHolder.itemView.setOnClickListener{
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you clicked view ${fruit.name}",
                Toast.LENGTH_SHORT).show()
        }

        //图片点击事件
        viewHolder.fruitImage.setOnClickListener{
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(parent.context, "you clicked view ${fruit.name}",
                Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    //对RecyclerView子项进行赋值，当子项滚动进屏幕时运行
    //通过position 获得当前实例
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

    //返回一共多少子项
    override fun getItemCount() = fruitList.size

}