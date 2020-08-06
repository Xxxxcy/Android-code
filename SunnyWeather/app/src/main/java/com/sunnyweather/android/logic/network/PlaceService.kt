package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//定义一个 用于访问 彩云天气城市搜索API的Retrofit接口

interface PlaceService {

    //调用searchPlaces时候，Retrofit会发送一条GET请求 访问GET注解中的地址
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //Retrofit会将服务器返回的JSON数据 自动解析成PlaceResponse对象
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}