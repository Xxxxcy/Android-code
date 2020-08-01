package com.example.uilayoutttest

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.title.view.*

import com.example.uilayoutttest.R
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class TitleLayout(context: Context, attrs:AttributeSet):LinearLayout(context, attrs) {

    //自定义一个控件
    //传入title.xml的布局
    init {
        LayoutInflater.from(context).inflate(R.layout.title,this)

        //注册2个点击事件
        //val titleBack = findViewById(R.id.titleBack)
        //val titleEdit = findViewById(R.id.titleEdit)
        titleBack.setOnClickListener{
            val activity = context as Activity
            activity.finish()
        }

        titleEdit.setOnClickListener{
            Toast.makeText(context,"You clicked Edit bouttn", Toast.LENGTH_SHORT).show()
        }
    }
}