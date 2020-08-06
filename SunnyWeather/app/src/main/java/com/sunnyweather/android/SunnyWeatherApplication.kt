package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//定义全局调用的 context
//SunnyWeatherApplication.context
class SunnyWeatherApplication : Application(){

    companion object {

        const val TOKEN = "t3cgXynYBf1enT9m"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}