package com.example.jetpacktest

import androidx.lifecycle.ViewModel

//添加一个参数 用于保存之前的计数
class MainViewModel(countReserved: Int) : ViewModel(){

    var counter = countReserved

}