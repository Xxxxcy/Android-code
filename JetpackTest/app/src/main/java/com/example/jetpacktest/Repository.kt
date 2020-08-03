package com.example.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//在单例类中 添加一个 get函数  通过每次传入的ID 创建一个新的User对象
object Repository {

    fun getUser(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        liveData.value = User(userId, userId, 0)
        return liveData
    }

}