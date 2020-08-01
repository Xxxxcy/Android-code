package com.example.activitylifecycletest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag,"onCreat")
        setContentView(R.layout.activity_main)

        //获取保存的零时数据
        if (savedInstanceState != null){
            val tempData = savedInstanceState.getString("data_key")
            Log.d(tag, tempData)
        }

        startNormalActivity.setOnClickListener{
            val intent = Intent(this, NromalActivity::class.java)
            startActivity(intent)
        }

        startDialogActivity.setOnClickListener{
            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")

    }

    override fun onRestart() {
        super.onRestart()
    }

    //回调方法 保证回收Activity前 数据会被调用
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val tempData = "something you just typed"
        outState.putString("data_key", tempData)
    }
}
