package com.example.uiwidgettest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AlertDialog


//函数式API 写监听按钮事件
class MainActivity : AppCompatActivity(), View.OnClickListener{
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button ->{
                /*
                获取edittext中的文字
                val inputText = editText.text.toString()
                Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()

                 */

                /*
                //进度条增加
                progressBar.progress = progressBar.progress + 10

                //点击按钮 更改图片
                //点击按钮让进度条消失和再现
                //visible 可见 gone 不可见且不占用屏幕
                if (progressBar.visibility == View.VISIBLE){
                    progressBar.visibility = View.GONE
                    imageView.setImageResource(R.drawable.img_2)
                }else{
                    progressBar.visibility = View.VISIBLE
                    imageView.setImageResource(R.drawable.img_1)
                }

                 */

                //对话框
                AlertDialog.Builder(this).apply{
                    setTitle("This is Dialog")
                    setMessage("Something important.")
                    setCancelable(false)
                    setPositiveButton("OK"){
                        dialog, which ->
                    }
                    setNegativeButton("Cancel"){
                        dialog, which ->
                    }
                    show()
                }
            }
        }
    }
}
