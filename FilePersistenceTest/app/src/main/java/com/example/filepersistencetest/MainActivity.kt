package com.example.filepersistencetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputText = load()//load 读取data中的文件
        if (inputText.isNotEmpty()){
            //若不为空 则填充到editText
            editText.setText(inputText)
            //将光标移动到文本末尾
            editText.setSelection(inputText.length)
            //弹出信息
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText = editText.text.toString()
        save(inputText)
    }

    private fun save(inputText: String){
        try {
            //java流
            //openFileOutput 得到 FileOutputStream对象 然后借助它 构建 OutoutStreamWriter对象
            //接着用 OutoutStreamWriter对象 构建一个 BufferedWriter对象 通过 BufferedWriter 将文本写入
            val output = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            //use 内置扩充函数在 Lanbda表达式执行完以后 自动将外层关闭 不用写finally手动关闭流
            //数据保存在 data 文件中
            writer.use {
                it.write(inputText)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    //还原输入框 输入的内容
    private fun load(): String{
        val content = StringBuilder()
        try {
            // 读取文件夹 data 中的文件 并返回一个FileInputStream 对象
            val input = openFileInput("data")
            //通过 FileInputStream对象 构建InputStreamReader对象后，再构建 BufferedReader对象，最后读取内容返回
            val reader = BufferedReader(InputStreamReader(input))
            reader.use{
                reader.forEachLine {
                    content.append(it)
                }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return content.toString()
    }
}