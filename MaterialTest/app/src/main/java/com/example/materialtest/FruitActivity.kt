package com.example.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fruit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class FruitActivity : AppCompatActivity() {

    companion object {

        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        //通过 Intent 获取传入的水果名和水果图片ID
        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: ""
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)

        //设置ToolBar 作为操作栏显示
        setSupportActionBar(toolbar)
        //启用Home按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //将水果名设置为标题 并传入图片
        collapsingToolBar.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruitImageView)
        //设置内容
        fruitContentText.text = generateFruitContent(fruitName)
    }

    //处理 HOME按钮的点击事件 关闭当前Activity 返回上一个Activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateFruitContent(fruitName: String) = fruitName.repeat(500)
}