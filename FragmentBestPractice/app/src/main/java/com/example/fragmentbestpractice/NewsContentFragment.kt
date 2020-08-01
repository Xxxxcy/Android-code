package com.example.fragmentbestpractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.news_content_frag.*

//传入布局
//双页模式布局创建
class NewsContentFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content_frag, container, false)
    }

    //将新闻的标题和内容显示在 定义的界面上
    fun refresh(title: String, content : String){
        contentLayout.visibility = View.VISIBLE
        newsTitle.text = title //刷新新闻标题
        newsContent.text = content //刷新新闻内容
    }
}