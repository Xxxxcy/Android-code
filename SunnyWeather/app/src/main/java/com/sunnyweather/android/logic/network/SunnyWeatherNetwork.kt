package com.sunnyweather.android.logic.network

import com.sunnyweather.android.logic.network.SunnyWeatherNetwork.getDailyWeather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork.getRealtimeWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//定义统一的网络数据源访问入口  对网络请求API进行封装

object SunnyWeatherNetwork {

    //创建一个 PlaceService接口的动态代理对象
    private val placeService = ServiceCreate.create(PlaceService::class.java)

    private val weatherService = ServiceCreate.create(WeatherService::class.java)


    //定义一个函数 调用在placeService接口中定义的searchPlaces方法 发起搜索城市数据的请求
    //声明为挂起函数 await函数 会堵塞当前协程 直到searchPlaces获得结果
    //外部调用 SunntWeatherNetwork的searchPlaces时候 Rertoit会立即发起网络请求 同时会阻塞协程 直到服务器响应后 await解析对象取出并返回 同时恢复当前协程 searchPlaces在得到await返回值以后将数据返回上一层
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}