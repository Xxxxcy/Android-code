package com.example.listviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

private val fruitList = ArrayList<Fruit>()

class MainActivity : AppCompatActivity() {
    /*
    private val data = listOf("apple","banana","orange","watermelon","pear",
        "grape","pineapple","strawberry","cherry","mango",
        "apple","banana","orange","watermelon","pear",
        "grape","pineapple","strawberry","cherry","mango")

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*数据无法直接传给ListView
        通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入
        适配器 ArrayAdapter 泛型 String
         */
        //val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        //listView.adapter = adapter

        initFruits()//初始化水果数据
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)//传入数据源 以及 布局ID
        listView.adapter = adapter//将适配器对象传递

        //注册点击事件
        //通过postion参数来判断 点击的是哪一个子项
        listView.setOnItemClickListener{
            //parent, view, position, id ->
            //未使用到的参数 可以用 _ 替代
            _, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun initFruits(){
    repeat(2){
        fruitList.add(Fruit("Apple",R.drawable.apple_pic))
        fruitList.add(Fruit("Banana",R.drawable.banana_pic))
        fruitList.add(Fruit("Orange",R.drawable.orange_pic))
        fruitList.add(Fruit("Watermelon",R.drawable.watermelon_pic))
        fruitList.add(Fruit("Pear",R.drawable.pear_pic))
        fruitList.add(Fruit("Grape",R.drawable.grape_pic))
        fruitList.add(Fruit("Pineapple",R.drawable.pineapple_pic))
        fruitList.add(Fruit("Strawberry",R.drawable.strawberry_pic))
        fruitList.add(Fruit("Cherry",R.drawable.cherry_pic))
        fruitList.add(Fruit("Mango",R.drawable.mango_pic))
    }
}