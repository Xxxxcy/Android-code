package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

//通过 sharedPreferences 对搜索的城市进行保存
object PlaceDao {

    //将Place对象储存在sharedPreferences对象中
    //在其中 先通过Gson将 place对象转换为 json
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    //现将Json对象读取出来 然后通过Gson将json转换为Place对象
    fun getSavedPlace(): Place {
        val placJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placJson, Place::class.java)
    }

    //判断数据是否已经被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}