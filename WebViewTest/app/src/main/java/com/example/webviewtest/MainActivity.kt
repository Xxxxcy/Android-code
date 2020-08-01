package com.example.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //使webView支持 javaScript 属性
        webView.settings.javaScriptEnabled

        //调用 setWebViewClient 方法并传入一个新的实例
        //作用为一个网页跳转到另外一个的时候 仍然在当前WebView显示不会打开系统浏览器
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.douyu.com/directory/all")
    }
}