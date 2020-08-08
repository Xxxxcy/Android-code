package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

//定义数据模型 城市信息数据

class PlaceResponse(val status: String, val places: List<Place>)

//通过注解 让JSON字段和Kotlin字段之间 建立映射关系
class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

class Location(val lng: String, val lat: String)