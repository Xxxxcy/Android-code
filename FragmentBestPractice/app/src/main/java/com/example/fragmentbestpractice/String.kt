package com.example.fragmentbestpractice

//将lettersCount 定义为 String 类的扩展函数
//函数中 自动拥有了String 实例的上下文
fun String.lettersCount(): Int{
    var count = 0
    for (char in this){
        if (char in this){
            count++
        }
    }
    return count
}