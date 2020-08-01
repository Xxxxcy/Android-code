package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

//发起HTTP请求 解析返回的XML JSON数据
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendRequetBtn.setOnClickListener {
            //sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }
    }


    private fun sendRequestWithHttpURLConnection(){

        //开启线程发起网络请求
        thread {

            var connection : HttpURLConnection? = null

            try {

                val response = StringBuilder()
                val url = URL("https://www.baidu.com")

                //获取HttpURLConnection
                //一般需要创建一个URL对象 并传入目标的网络地址然后调用 openConnection()方法
                connection = url.openConnection() as HttpURLConnection
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
                showResponse(response.toString())
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                //关闭HTTP连接
                connection?.disconnect()
            }
        }
    }



    private fun sendRequestWithOkHttp(){
        //开启线程发起网络请求
        //在子线程中 发送一条HTTP请求
        thread {
            try {
                //创建一个实例
                val client = OkHttpClient()
                //创建一个 Request对象
                val request = Request.Builder()
                        //指定访问的服务器地址是计算机本机
                        //.url("http://10.0.2.2/get_data.xml")
                        .url("http://10.0.2.2/get_data.json")
                        .build()
                //创建一个Call对象 并调用 execute 来发送请求并获取服务器返回的数据
                val response = client.newCall(request).execute()
                //response对象就是服务器返回的数据 得到具体内容
                val responseData = response.body?.string()

                if (responseData != null){
                    //showResponse(responseData)
                    //parseXMLWithPull(responseData)
                    //parseXMLWithSAX(responseData)
                    //parseJSONWithJSONObject(responseData)
                    parseJSONWithGSON(responseData)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun showResponse(response: String){
        runOnUiThread{
            //在此处进行UI操作,将结果显示在界面上
            responseText.text = response
        }
    }

    private fun parseXMLWithPull(xmlData: String) {
        try {
            //创建一个实例
            val factory = XmlPullParserFactory.newInstance()
            //得到实例对象
            val xmlPullParser = factory.newPullParser()
            //将服务器返回的XML设置进入
            xmlPullParser.setInput(StringReader(xmlData))
            //getevenType 得到当前解析事件
            var eventType = xmlPullParser.eventType

            var id = ""
            var name = ""
            var version = ""

            //对事件 不停的解析 直到完成条件
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //获取当前节点的名字
                val nodeName = xmlPullParser.name
                when (eventType) {
                    // 开始解析某个节点
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    // 完成解析某个节点
                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {
                            Log.d("MainActivity", "id is $id")
                            Log.d("MainActivity", "name is $name")
                            Log.d("MainActivity", "version is $version")
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseXMLWithSAX(xmlData: String) {
        try {
            //创建SAXParserFactory对象
            val factory = SAXParserFactory.newInstance()
            //获取xmlReader 对象
            val xmlReader = factory.newSAXParser().getXMLReader()
            val handler = ContentHandler()
            //将ContentHandler的实例设置到XMLReader中
            xmlReader.contentHandler = handler
            //开始执行解析
            xmlReader.parse(InputSource(StringReader(xmlData)))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    //服务器中定义的是 JSON数组
    private fun parseJSONWithJSONObject(jsonData: String) {
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")

                Log.d("MainActivity", "id is $id")
                Log.d("MainActivity", "name is $name")
                Log.d("MainActivity", "version is $version")
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        //借助 TypeToken 将期望解析成的数据类型传入 fromJson中
        val typeOf = object : TypeToken<List<App>>(){}.type
        val appList = gson.fromJson<List<App>>(jsonData, typeOf)
        for (app in appList) {
            Log.d("MainActivity", "id is ${app.id}")
            Log.d("MainActivity", "name is ${app.name}")
            Log.d("MainActivity", "version is ${app.version}")
        }
    }
}