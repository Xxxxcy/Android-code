package com.example.servicetest

import android.app.IntentService
import android.content.Intent
import android.util.Log

//创建一个 异步、自动停止的Service

//调用父类的 构造函数 并传入一个字符串
//IntentService 会自动停止运行
class MyIntentService : IntentService("MyIntentService") {
    //子类中重写 onHandleIntent 因为在子线程中运行 所以可以处理一些 耗时逻辑而不用担心 ANR问题
    override fun onHandleIntent(p0: Intent?) {
        //打印当前线程ID
        Log.d("MyIntentService", "Thread id is ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestory executed")
    }
}