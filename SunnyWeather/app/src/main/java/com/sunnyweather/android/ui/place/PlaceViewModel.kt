package com.sunnyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

//定义ViewModel层 逻辑层和UI层之间的桥梁

class PlaceViewModel : ViewModel() {

    //每当searchPlaces调用时候 switchMap对应的转化函数就会实行
    //仓库层中的 Repository.searchPlaces 就会发起网络请求将 仓库层返回的LiveData对象 变成一个可以观察的LiveData对象

    private val searchLiveData = MutableLiveData<String>()

    //对界面上的 城市数据进行缓存 保证在旋转的时候不会消失
    val placeList = ArrayList<Place>()

    //使用 Transformations.switchMap观察对象  否则仓库层返回的 LiveData对象无法进行观察
    val placeLiveData = Transformations.switchMap(searchLiveData){query ->
        Repository.searchPlaces(query)
    }

    //将传入的搜索值参数 赋值给 searchLiveData 对象
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    //sharedPreferences 对搜索的城市进行保存 业务逻辑和 PlaceViewModel也相关 需要在其中也封装
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}