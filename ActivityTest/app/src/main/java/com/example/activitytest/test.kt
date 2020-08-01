package com.example.activitytest

import java.lang.StringBuilder


fun main(){
    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
    //构建builder对象字符串并打印
    /*

    val buffer = StringBuffer()
    buffer.append("Start eating fruits. \n")

    for (fruit in list) {
        buffer.append(fruit).append("\n")
    }

    buffer.append("Ate all fruits.")
    val result = buffer.toString()

     */
    /*
    //with函数的使用
    val result = with(StringBuilder()){
        append("Start eating ftuits. \n")
        for (furit in list){
            append(furit).append("\n")
        }
        append("Ate all fruits.")
        toString()
    }

     */
    /*
    //run函数的使用
    val result= StringBuilder().run(){
        append("Start eating ftuits. \n")
        for (furit in list){
            append(furit).append("\n")
        }
        append("Ate all fruits.")
        toString()
    }
    println(result)

     */

    //apply 函数无法返回指定值 只能返回对象本身 打印时候需要调用toString方法
    val result= StringBuilder().apply(){
        append("Start eating ftuits. \n")
        for (furit in list){
            append(furit).append("\n")
        }
        append("Ate all fruits.")

    }
    println(result.toString())

}


