package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

//仓库层的实现
//判断调用方请求的数据用该是从本地数据源中获取还是从网络数据源中获取并返回给调用方
//在仓库层中 一般为了实现异步响应返回通知给上一层 通常会返回一个LIveData方法
object Repository {

    //liveData()函数 会自动创建liveData对象 并将上下文挂起 可以调用任意挂起函数
    //Dispatchers.IO 所有代码运行在子线程中 Android 不允许在主线程中进行网络请求
    //fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        //val result = try {

            //搜索城市数据
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)

            if (placeResponse.status == "ok"){
                val place = placeResponse.places
                //包装获取的城市数据列表
                Result.success(place)
            }else{
                //包装一个异常信息
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        //}catch (e: Exception){
            //Result.failure<List<Place>>(e)
        }
        //将包装的结果发出去
        //类似于 LiveData中的setvalue方法通知数据变化 但是当前无法直接返回livedata对象
        //emit(result)
    //}


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //val result = try {

            //建立一个协程作用域 使用async
            coroutineScope {

                //通过async并行发起请求
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }

                //2个请求都完成后 才会进一步执行程序
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {

                    //将 realtime daily对象取出并封装到weather中
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)

                    //包装weather对象
                    Result.success(weather)
                }else{

                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        //}catch (e: Exception){
            //Result.failure<Weather>(e)
        //}
        //emit(result)
    }

    /*
        简化上面的代码 fire是按照livedata函数的参数标准定义的一个高阶函数
        先调用 liveData 函数 然后在内部中统一进行 tey catch处理

        block: suspend () 声明中 加入 挂起关键字 让传入的代码也是有挂起函数上下文
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    //仓库层实现
    //在仓库层 做了一层接口封装  但业务逻辑和 PlaceViewModel也相关 需要在其中也封装
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}