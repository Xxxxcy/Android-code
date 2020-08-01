package com.example.databasetest

import android.content.ContentValues
import androidx.core.content.contentValuesOf

//vararg 可变参数列表
//Pair 一种键值对数据结构
//ContentValues的所有 键都是String类型 值有多种类型
//Pair值的泛型为 Any? Any 为Kotlin 中所有类的共同基类
//fun cvof(vararg pairs: Pair<String, Any?>): ContentValues {

fun cvof(vararg pairs: Pair<String, Any?>) = ContentValues().apply {

    //构建一个 ContentValues对象
    //val cv = ContentValues()

    // 遍历pairs列表
    for (pair in pairs){

        val key = pair.first
        val value = pair.second
        when (value){
            //数据填入 ContentValues 中
            //value 会自动转换相应的类型
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Short -> put(key, value)
            is Float -> put(key, value)
            is Double -> put(key, value)
            is Boolean -> put(key, value)
            is String -> put(key, value)
            is Byte -> put(key, value)
            is ByteArray -> put(key, value)
            null -> putNull(key)
        }
    }
    //return cv
}