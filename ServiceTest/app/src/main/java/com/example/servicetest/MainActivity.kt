package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

//Activity与Service 进行通信
class MainActivity : AppCompatActivity() {

    //声明 但是不初始化
    lateinit var downloadBinder: MyService.DownloadBinder

    // 创建一个 ServiceConnection 匿名类 并重写 2个方法
    private val connection = object : ServiceConnection{
        // 在Activity 和 Service 绑定的时候调用
        override fun onServiceConnected(name: ComponentName, service: IBinder) {

            //获取 downloadBinder 实例
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDpwnload()
            downloadBinder.getProgress()
        }

        //Service 崩溃或者被杀死的时候调用
        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindServiceBtn.setOnClickListener {

            val intent = Intent(this, MyService::class.java)

            bindService(intent, connection, Context.BIND_AUTO_CREATE) //绑定Service
        }

        unbindServiceBtn.setOnClickListener {

            val intent = Intent(this, MyService::class.java)

            unbindService(connection) //解除绑定
        }

        startIntentServiceBtn.setOnClickListener {
            //打印主线程ID
            Log.d("MainActivity", "Thread id is ${Thread.currentThread().name}")
            val intent = Intent(this, MyIntentService::class.java)
            startService(intent)
        }
    }
}