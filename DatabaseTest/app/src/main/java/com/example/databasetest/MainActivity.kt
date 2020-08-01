package com.example.databasetest

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //构造一个 MyDatabaseHelper对象 确定 名称和版本号
        //当版本号 大于1 可执行onUpgrade
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)

        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            /*
            val values1 = ContentValues().apply {
                //开始组装第一条数据
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }

             */

            val values1 = cvof("name" to "The Da Vinci Code","author" to "Dan Brown",
            "pages" to 454, "price" to 16.96)

            //插入第一条数据 参数1：表名 参数二：一般为null
            //参数3：ContentValues对象 提供了 put重载 向其中添加数据
            db.insert("Book", null, values1)//插入第一条数据

            val values2 = ContentValues().apply {
                //开始组装第二条数据
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 19.95)
            }
            db.insert("Book", null, values2)
        }

        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            //构建一个 ContentValues对象
            val values = ContentValues()
            values.put("price", 10.99)
            //第三个参数 对应的是SQL语句的 where部分
            //更新所有 name = ?的地方  ？由 第四个参数提供 arrayof 是一个快捷创建数组的方法
            db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }

        deleteData.setOnClickListener{

            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }

        queryData.setOnClickListener{

            val db = dbHelper.writableDatabase

            //查询Book表中所有的数据
            //返回一个 cursor 对象
            val cursor = db.query("Book", null, null, null, null, null, null)
            //移动到第一个位置
            if (cursor.moveToFirst()){
                do{
                    //遍历cursor表
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))

                    Log.d("MainActivity", "book name is $name")
                    Log.d("MainActivity", "book author is $author")
                    Log.d("MainActivity", "book pages is $pages")
                    Log.d("MainActivity", "book price is $price")

                }while (cursor.moveToNext())
            }
            //关闭对象
            cursor.close()
        }

        //事务测试  事务：事件同时完成 或者 一个都不完成
        replaceData.setOnClickListener {

            val db = dbHelper.writableDatabase
            db.beginTransaction() //开启事务
            try {
                db.delete("Book", null, null)
                if (true){
                    //手动抛出一个异常 让事务失败
                    //throw NullPointerException()
                }
                val values = ContentValues().apply {
                    put("name", "Game of Thrones")
                    put("author", "George Martin")
                    put("pages", 720)
                    put("price", 20.85)
                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful()//事务执行成功
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                db.endTransaction()//结束事务
            }
        }
    }
}