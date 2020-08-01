package com.example.retrofittest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Retrofit 构建器的最佳写法

//object关键字创建一个单例类
object ServiceCreator {
    //指定 Retrofit的根路径
    private const val BASE_URL = "http://10.0.2.2/"

    //通过Retrofit.Builder 构建一个 Retrofit对象
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //提供一个外部可见的 create方法
    //当外部调用的时候 实际是调用了Retrofit对象的 create方法 从而创建出相应的 Service接口动态代理对象
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}

/*
获取一个 AppService接口的动态对象
val appService = ServiceCreate.create(AppService::class.java)
 */