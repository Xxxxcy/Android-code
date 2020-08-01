package com.example.networktest

import java.lang.Exception

interface HttpCallbakListener {
    //成功响应服务器的时候调用
    fun onFinish(response: String)
    //当网络操作出现错误的时候调用
    fun onError(e :Exception)

}