package com.example.materialtest

import android.content.Context
import android.widget.Toast

//给String 和 Int 类各自添加一个扩充函数 并且在其中封装 Toast 逻辑
//动态改变时长
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun Int.showToast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

//一段文字提醒
// “This is Toast".showToast(context, Toast.LENGH_LONG)