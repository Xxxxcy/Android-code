package com.example.jetpacktest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

//添加一个参数 用于保存之前的计数
class MainViewModel(countReserved: Int) : ViewModel(){

    //保证ViewModel的封装性  只暴露不可变的LiveData给外界
    val counter : LiveData<Int>
        get() = _counter

    //定义一个 MutableLiveData对象 并指定泛型
    private  val _counter = MutableLiveData<Int>()

    //通过LiveData 来通知观察
    init {
        //保存之前的参数
        //setvalue 在主线程中调用 设置数据
        _counter.value = countReserved
    }

    fun plusOne() {

        //若 getvalue 得到的数为0 则用 ?: 运算符 用0 作为默认计数
        val count= _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }

    //定义一个 userIdLiveData 用来观察 UserId 的变化
    private val userIdLiveData = MutableLiveData<String>()

    //定义一个 get 函数 调用 Repository 的 get 函数来回去 LiveData 对象
    fun getUser(userId: String) {
        userIdLiveData.value = userId
    }

    //传入的UserId 设置在 userIdLiveData 中 一旦发生变化就会调用 switchMap
    //switchMap其中转换函数(Repository.getUser) 返回真实值
    //Repository.getUser返回的 LiveData对象会转换成一个可观察的 LiveData对象
    val user: LiveData<User> = Transformations.switchMap(userIdLiveData) { userId ->
        Repository.getUser(userId)
    }

    //ViewModel中获取数据的方法可能是没有参数的
    private val refreshLiveData = MutableLiveData<Any?>()

    val refershResult = Transformations.switchMap(refreshLiveData) {
        //调用一次赋值函数 有数据变化 就可以对新数据进行 变换 观察
        //Repository.refresh()
        Repository.getUser("")
    }

    fun refresh() {
        //重新进行一个赋值 可以出发一次数据变化
        refreshLiveData.value = refreshLiveData.value
    }

}