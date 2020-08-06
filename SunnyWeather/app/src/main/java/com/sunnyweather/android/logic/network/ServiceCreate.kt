package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Retrofit 构建器详解 11.6.3 P460
//上层接口封装 应用层通信
object ServiceCreate {

    //指定根路径
    private const val BASE_URL = "https://api.caiyunapp.com/"

    //在内部构建一个对象
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //对外提供一个函数 接收Class参数 外部调用的时候 相当于 调用Retrofit.create函数
    //从而创建出对应的Service接口的动态代理对象
    fun <T> create(serviceClass: Class<T>) : T = retrofit.create(serviceClass)

    //lnline修饰方法 reified修饰泛型 -> 泛型实例化
    //运用 T::class.java 简化调用
    inline fun <reified T> create() : T = create(T::class.java)
}