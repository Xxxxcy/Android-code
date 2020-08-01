package com.example.providertest

import android.content.UriMatcher
import android.util.Log
import kotlin.reflect.KProperty

/*
    //by 委托关键字  lazy 高阶函数
    //lazy 会创建并返回一个Delegate对象
    //对 p 调用的时候 调用的是Delegate的getvalue函数 探后传入lazy 的 Lambda表达式中
    val p by lazy{}
 */


//编写自己的 later 懒加载函数

//不定义在任何类的函数才是顶层函数
//创建 Later 实例 将接受的函数类型参数 传给Later类的构造函数
fun <T> later(black: () -> T) = Later(black)


class Later <T>(val black: () -> T){
    var value: Any? = null

    operator fun getValue(any: Any?, prop:KProperty<*>): T{
        if (value == null){
            value = black()
        }
        return value as T
    }
}

val uriMatcher by later {

    val matcher = UriMatcher(UriMatcher.NO_MATCH)
    matcher.addURI()
    matcher.addURI()
    matcher.addURI()
    matcher.addURI()
    matcher
}

//测试 懒加载功能是否生效
val p by later {
    Log.d("TAG", "run codes inside later block")
    "test later"
}