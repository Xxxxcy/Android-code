package com.example.activitytest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.first_layout.*

class FitstActivity : BaseActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //自动调用了父类的 getMenuInflater()方法
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //使用语法糖 自动调用了 grtItemid()方法
        when (item.itemId){
            R.id.add_item -> Toast.makeText(this, "You clicked Add",
                Toast.LENGTH_SHORT).show()

            R.id.remove_item -> Toast.makeText(this, "You clicked Remove",
                Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //加载一个布局
        setContentView(R.layout.first_layout)
        //Log.d("FirstActivity", this.toString())
        //打印当前返回栈的ID
        Log.d("FirstActivity", "Task id is $taskId")

        /*
        //获取在布局文件中定义的元素
        val button1:Button = findViewById(R.id.button1)
        button1.setOnClickListener{
            //弹出消息
            Toast.makeText(this, "You clicked Button 1", Toast.LENGTH_SHORT).show()
        }

         */
        //使用kotlin-android-extensions插件
        //根据布局文件的控件ID 自动生成相应的变量
        button1.setOnClickListener {
            //弹出消息
            //Toast.makeText(this, "You clicked Button 1", Toast.LENGTH_SHORT).show()

            //首先构造一个Intent对象 传入this(FirstActivity)作为上下文
            //第二个为目标参数
            //显示Intent
            //val intent = Intent(this, SecondActivity::class.java)

            //隐式
            //val intent = Intent("com.example.activitytest.ACTION_START")
            //intent.addCategory("com.example.activitytest.MY_CATEGORY")

            //调用系统的浏览器打开网页
           // val intent = Intent(Intent.ACTION_VIEW)
            //intent.data = Uri.parse("http://www.baidu.com")

            //调用拨号界面
            //val intent = Intent(Intent.ACTION_DIAL)
            //intent.data = Uri.parse("tel:10086")

            //传递数据
            //putExtra方法的重载 将传递的数据暂时存在Intent中
            //val data = "Hello SecondActivity"
            //val intent = Intent(this, SecondActivity::class.java)
            //intent.putExtra("extra_data", data)
            //startActivity(intent)

            //接受返回数据
            //使用startActivityForResult 来启动 SeccondActivity
            //val intent = Intent(this, SecondActivity::class.java)
            //startActivityForResult(intent,1)

            //Standard 模式
            //新的Activity 位于返回栈的栈顶
            //val intent = Intent(this, FitstActivity::class.java)
            //startActivity(intent)

            SecondActivity.actionStart(this,"data1", "data2")
        }
    }

    //重写方法 得到SeccondActivity的返回数据
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            1 -> if (requestCode == Activity.RESULT_OK){
                val returnData = data?.getStringExtra("data_return")
                Log.d("FirstAcivity", "return data is $returnData")
            }
        }
    }
}
