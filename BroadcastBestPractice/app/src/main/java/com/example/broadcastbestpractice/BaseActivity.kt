package com.example.broadcastbestpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

//创建一个类 作为所有类的父类
//并且创建一个广播接收器
open class BaseActivity :AppCompatActivity(){

    lateinit var receiver:ForceofflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    //在 onResume和onPause中注册和取消 保证在栈顶的Activity才可以收到这条广播
    //注册 ForceofflineReceiver
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE")
        receiver = ForceofflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    //取消注册 ForceofflineReceiver
    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceofflineReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context,intent: Intent) {
            //构建一个对话框
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("You aro forced to be offline. Please try to login again.")
                setCancelable(false) //对话框不可取消
                //给对话框注册确定按钮
                setPositiveButton("OK"){_,_->
                    ActivityCollector.finishAll() //销毁所有Activity
                    val i = Intent(context, LoginActivity::class.java)
                    context.startActivity(i) //重新启动LoginActivity
                }
                show()
            }
        }
    }
}