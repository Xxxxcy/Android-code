package com.example.jetpacktest

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

//定义一个后台任务
//没一个后台任务都要继承自 Worker类 并且调用唯一的构造函数 在其中编辑逻辑函数即可
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("SimpleWorker", "do work in SimpleWorker")
        return Result.success()
    }
}