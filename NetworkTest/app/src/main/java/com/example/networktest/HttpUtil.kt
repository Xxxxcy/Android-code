package com.example.networktest

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread

//创建一个HTTP发送请求的公共类 并提供公共方法
//但是没有在内部设置 子线程 可能造成堵塞
//通过 回调机制 来解决
object HttpUtil {

    fun sendHttpRequest(address: String, listener: HttpCallbakListener){

        thread {

            var connection: HttpsURLConnection? = null
            try {

                val response = StringBuilder()
                val url = URL(address)

                //获取HttpURLConnection
                //一般需要创建一个URL对象 并传入目标的网络地址然后调用 openConnection()方法
                connection = url.openConnection() as HttpsURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000

                //获得服务器的输入流
                val input = connection.inputStream

                //下面对获取到的输入流进行读取
                var reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                //return response.toString()

                //回调onFinish()方法
                listener.onFinish(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                //return e.message.toString()

                //回调 onError()方法
                listener.onError(e)
            } finally {
                //关闭HTTP连接
                connection?.disconnect()
            }
        }
    }

    //okhttp3.Callback 是OKHTTP中 自带的回调函数
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()

        //在enqueue 内部已经帮我们开好了子线程
        client.newCall(request).enqueue(callback)
    }
}

/*
调用 sendHttpRequest 未开启子线程
val address = "http://"
val response = HttpUtil.sendHttpRequest(address)
 */

/*
调用sendHttpRequest 开启子线程
HttpUtil.sendHttpRequest(address: String, listener: HttpCallbakListener{
    override fun onFinish(response: String){
        //得到服务器返回的具体内容
    }

    override fun onError(e: Exception){
        //对一场情况进行处理
    }
})
 */

/*
HttpUtil.sendOkHttpRequest(address, object : Callback{
    override fun onFinish(call: Call, response:Response){
        //得到服务器返回的具体内容
        val responseData = responre.body?.string()
    }

    override fun onError(call:Call, e: Exception){
        //对一场情况进行处理
    }
 */