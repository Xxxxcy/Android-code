package com.example.androidthreadtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

//异步消息处理机制
//在子线程中更新UI
class MainActivity : AppCompatActivity() {

    //定义一个整型变量 用于表示更新这个动作
    val updateText = 1

    val handler = object : Handler() {
        //接受到发出的消息 handleMessage对其进行处理 handleMessage的进程在主线中
        override fun handleMessage(msg: Message) {
            // 在这里可以进行UI操作
            when (msg.what) {
                updateText -> textView.text = "Nice to meet you"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeTextBtn.setOnClickListener {
            // thread 是 Kotlin 内置的顶层函数 不需要start() 直接在函数中写逻辑就行
            thread {
                // Android 不允许在子线程中 进行UI操作
                //textview.text = "Nice to meet you"

                //创建一个Message对象 并且将 what字段 指定为updateText
                val msg = Message()
                msg.what = updateText
                //将消息发送出去
                handler.sendMessage(msg)
            }
        }
    }
}