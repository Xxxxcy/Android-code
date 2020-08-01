package com.example.retrofittest

import retrofit2.Call
import retrofit2.http.GET

//静态类型接口
interface AppService {
    //添加一个注解 在调用getAppData的时候会发起一条get请求 地址是传入的参数
    @GET("get_data.json")
    fun getAppData(): Call<List<App>>

}