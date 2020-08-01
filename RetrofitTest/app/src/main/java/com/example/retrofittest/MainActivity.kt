package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Rotrifit网络库的用法
//Rotrifit解析JSON数据
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAppDataBtn.setOnClickListener {

            //创建一个 Retrifit 对象
            val retrofi = Retrofit.Builder()
                .baseUrl("http://10.0.2.2/")
                //指定Retrifit对象在解析数据时 转换需要的库
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            //传入具体 Service 接口 所对应的Class类型  创建一个该接口的动态代理对象
            val appService = retrofi.create(AppService::class.java)
            //getAppData 返回的是 Callback<List<App>>对象
            //然后调用 enqueue()方法 开启子线程 Retrifit会根据 注解 的服务地址进行网络请求
            appService.getAppData().enqueue(object : Callback<List<App>> {
                //onResponse 方法中 response.body()会得到 Retrifit 解析后的对象 ->List<App>
                //最后遍历 List 将数据打印出来
                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                    val list = response.body()
                    if (list != null) {
                        for (app in list) {
                            Log.d("MainActivity", "id is ${app.id}")
                            Log.d("MainActivity", "name is ${app.name}")
                            Log.d("MainActivity", "version is ${app.version}")
                        }
                    }
                }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}