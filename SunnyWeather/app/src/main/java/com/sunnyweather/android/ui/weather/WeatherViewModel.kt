package com.sunnyweather.android.ui.weather

import androidx.lifecycle.*
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    //使用swtichaMap 观察 locationLiveData对象
    //且在swtichaMap中 调用仓库层定义的refreshWeather 这样仓库层返回的LiveData对象可以转为一个可以观察的对象
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    //定义方法来刷新天气 将传入的经纬度封装成一个 Location对象赋值给locationLiveData
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}