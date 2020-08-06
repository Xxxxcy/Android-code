package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.network.SunntWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

//仓库层的实现
//判断调用方请求的数据用该是从本地数据源中获取还是从网络数据源中获取并返回给调用方
//在仓库层中 一般为了实现异步响应返回通知给上一层 通常会返回一个LIveData方法
object Repository {

    //liveData()函数 会自动创建liveData对象 并将上下文挂起 可以调用任意挂起函数
    //Dispatchers.IO 所有代码运行在子线程中 Android 不允许在主线程中进行网络请求
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {

            //搜索城市数据
            val placeResponse = SunntWeatherNetwork.searchPlaces(query)

            if (placeResponse.status == "ok"){
                val place = placeResponse.places
                //包装获取的城市数据列表
                Result.success(place)
            }else{
                //包装一个异常信息
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e: Exception){
            Result.failure<List<Place>>(e)
        }
        //将包装的结果发出去
        //类似于 LiveData中的setvalue方法通知数据变化 但是当前无法直接返回livedata对象
        emit(result)
    }

}