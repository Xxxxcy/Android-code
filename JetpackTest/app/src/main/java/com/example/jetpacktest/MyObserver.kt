package com.example.jetpacktest

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

// Lifecycles 让任何一个类都可以感知Activity的生命周期

//传递对象 主动感知生命周期
class MyObserver(val lifecycle: Lifecycle) : LifecycleObserver {

    //调用注解 传入生命周期事件
    //分别在 生命周期开始和结束的时候调用
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
        Log.d("MyObserver", "activityStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
        Log.d("MyObserver", "activityStop")
    }
}