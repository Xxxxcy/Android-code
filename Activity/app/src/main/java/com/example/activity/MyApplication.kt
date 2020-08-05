package com.example.activity

import android.app.Application
import android.content.Context

//创建一个自己的MyApplication 继承自Application
//全局获取context -> MyApplication.context
class MyApplication : Application() {

    companion object{
        //将context设置为静态变量的话 容易泄露内存
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}