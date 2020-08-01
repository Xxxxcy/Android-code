package com.example.retrofittest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExampleService {

    //通过 {page} 占位符添加参数， 使用 @Path 注解来声明这个函数
    //当调用getData时候 Retrifit自动将page参数替换到占位符的位置
    @GET("{page}/get_data.json")
    fun getData(@Path("page") page: Int): Call<Data>
}