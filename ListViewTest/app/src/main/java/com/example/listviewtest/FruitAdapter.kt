package com.example.listviewtest

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

//自定义适配器
//继承于 ArrayAdapter 泛型 Fruit
class FruitAdapter(activity:Activity, val resouceId:Int,data:List<Fruit>) :
    ArrayAdapter<Fruit>(activity, resouceId, data){

    //定义内部类
    inner class ViewHolder(val fruitImage:ImageView, val fruitName:TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //val <标识符> : <类型> = <初始化值>
        //convertView 对之前加载好的布局进行缓存
        val view :View
        //创建一个ViewHolder对象
        val viewHolder :ViewHolder
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(resouceId, parent, false)
            //将实例缓存在ViewHolder中  避免findViewById重复使用
            val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
            val fruitName: TextView = view.findViewById(R.id.fruitName)
            viewHolder = ViewHolder(fruitImage, fruitName)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val fruit = getItem(position)//获取当前实例
        if (fruit != null){
            viewHolder.fruitImage.setImageResource(fruit.imageId)
            viewHolder.fruitName.text = fruit.name
        }

        /*
        //koylin-android-extensions插件在ListVew无法生效 需要用findViewById 来获取布局和实例
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName:TextView = view.findViewById(R.id.fruitName)
        val fruit = getItem(position)//获取当前实例

        if (fruit != null){
            fruitImage.setImageResource(fruit.imageId)
            fruitName.text = fruit.name
        }

         */
        return view
    }
}