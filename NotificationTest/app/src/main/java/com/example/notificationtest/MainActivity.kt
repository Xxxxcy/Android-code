package com.example.notificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

//创建一个通知
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //创建一个 NotificationManager 对通知进行管理
        //字符串参数 用于获取系统的哪个服务
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        //对系统版本进行判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //用NotificationChannel类 构建一个通知渠道
            //创建一个通知渠道 需要3个参数 1：渠道id  2：渠道名称  3:通知重要等级
            val channel = NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)

            val channe2 = NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channe2)
        }

        sendNotice.setOnClickListener {

            //通知栏 点击事件
            //通过intent 表达想要启动 NotificationActivity 的意图
            //将 intent 对象传入 PendingIntent.getActivity 中得到 PendingIntent实例 pi
            val intent = Intent(this, NotificationActivity::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, 0)

            //NotificationCompat类 通过Builder构造器 创建notification 保证在所有安卓版本上都可以使用
            //并且通过调用对象的设置方法丰富对象
            val notification = NotificationCompat.Builder(this, "important")
                    .setContentTitle("This is content title")
                    //.setContentText("This is content text")
                    //.setStyle(NotificationCompat.BigTextStyle()
                        //.bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                    .setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_image)))
                    .setSmallIcon(R.drawable.small_icon)//通知小图标  系统栏
                    .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.large_icon))//通知大图标 下拉栏
                    .setContentIntent(pi) //传入 PendingIntent 实例
                    .setAutoCancel(true) //点击下拉栏后 自动消失
                    .build()

            //保证 id唯一
            manager.notify(1, notification)
        }
    }
}