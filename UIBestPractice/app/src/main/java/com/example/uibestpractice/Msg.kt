package com.example.uibestpractice

//content 消息的内容  type 消息的类型
class Msg (val content:String, val type:Int){
    companion object{
        //一条收到的信息
        const val TYPE_RECEIVED = 0

        //一条发出的信息
        const val TYPE_SENT = 1
    }
}