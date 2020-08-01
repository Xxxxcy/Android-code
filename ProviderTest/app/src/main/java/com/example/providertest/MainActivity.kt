package com.example.providertest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

//跨程序共享数据  ContentPrivider
class MainActivity : AppCompatActivity() {

    var bookId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData.setOnClickListener{
            //添加数据
            //调用 Uri.parse 将 内容URI 析构成 Uri
            val uri = Uri.parse("content://com.example.databasetest.provider/book")

            val values = contentValuesOf("name" to "A Clash of Kings",
            "author" to "George Martin", "pages" to 1040, "price" to 22.85)

            //insert 方法会返回一个Uri对象 其中包含了新增数据的id 通过pathSegments 取出
            val newUri = contentResolver.insert(uri, values)
            bookId = newUri?.pathSegments?.get(1)
        }

        queryData.setOnClickListener {
            //查询数据
            //结果存放在 cursor中 对其进行遍历
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()){
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getString(getColumnIndex("pages"))
                    val price = getString(getColumnIndex("price"))

                    Log.d("MainActivity", "book name is $name")
                    Log.d("MainActivity", "book author is $author")
                    Log.d("MainActivity", "book pages is $pages")
                    Log.d("MainActivity", "book price is $price")
                }
                close()
            }
        }

        updateData.setOnClickListener {
            //更新数据
            //在 uri 尾部加上了 id 不会对其他表产生影响
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                val values = contentValuesOf("name" to "A Strom of Swords",
                "pages" to 1216, "price" to 24.05)

                contentResolver.update(uri, values, null, null)
            }
        }

        deleteData.setOnClickListener {
            //删除数据
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                contentResolver.delete(uri, null, null)
            }
        }
    }
}