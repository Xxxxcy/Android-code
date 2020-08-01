package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFruits()//初始化水果数据
        //指定RcyclerView的布局方式 为线性布局 类似于ListView
        //val layoutManager = LinearLayoutManager(this)
        //横向布局
        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        //瀑布流布局
        val layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        //创建适配器实例 将水果数据传入构造函数中
        val adapter = FruitAdapter(fruitList)
        //调用setAdapter()方法 完成适配器设置 数据与RecyclerView之间的关联完成
        recyclerView.adapter = adapter
    }

    private fun initFruits() {
        repeat(2) {
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.apple_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.banana_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.orange_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.watermelon_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.pear_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.grape_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.pineapple_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.strawberry_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.cherry_pic))
            fruitList.add(Fruit(getRandonLengthString("Apple"), R.drawable.mango_pic))
        }
    }

    private fun getRandonLengthString(str: String):String{
        val n = (1..5).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(str)
        }
        return builder.toString()
    }
}