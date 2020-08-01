package com.example.playvideotest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //调用 parse方法 将raw下的VIDEO文件 解析成Uri对象
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        //将对象传入 VideoView对象初始化完成
        videoView.setVideoURI(uri)

        play.setOnClickListener {
            if (!videoView.isPlaying){
                videoView.start() //开始播放
            }
        }

        pause.setOnClickListener {
            if (videoView.isPlaying){
                videoView.pause() //暂停播放
            }
        }

        replay.setOnClickListener {
            if (videoView.isPlaying){
                videoView.resume() //重新播放
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.suspend()
    }
}