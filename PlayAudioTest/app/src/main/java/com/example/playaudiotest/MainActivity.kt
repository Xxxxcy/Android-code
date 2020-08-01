package com.example.playaudiotest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //类初始化 创建实例
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //对 对象进行初始化
        initMediaPlayer()
        play.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start() //开始播放
            }
        }

        pause.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause() //播放暂停
            }
        }

        stop.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.reset() //停止播放
                initMediaPlayer()
            }
        }
    }

    private fun initMediaPlayer(){
        //通过getAssets 得到一个AssetManager实例 可以用来读取assets目录下的任何资源
        val assetManager = assets
        //将 音频文件的句柄打开
        val fd = assetManager.openFd("music.mp3")

        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}