package com.example.uibestpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val msgList = ArrayList<Msg>()
    // 在后续使用adapter时候 都需要判空
    //private var adapter: MsgAdapter? = null

    //lateinit 晚一点回去尽心初始化 可不用再最开始赋值null
    //但是一定要保证后续有初始化
    private lateinit var adapter :MsgAdapter

    //构建recyclerView
    //指定layoutManager 和 适配器
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMsg()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //判断是否全局变量完成初始化
        if (!::adapter.isInitialized){
            adapter = MsgAdapter(msgList)
        }
        recyclerView.adapter = adapter
        send.setOnClickListener(this)
    }

    //重写send 按键的事件
    override fun onClick(v: View?){
        when (v){
            send -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()){
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
                    //有新消息时,刷新RecyclerView中的显示
                    //通知有新的信息插入
                    //lateinit 关键字后 未赋值null 可不判空
                    adapter.notifyItemInserted(msgList.size - 1)
                    //将RecyclerView定位到最后一行
                    recyclerView.scrollToPosition(msgList.size - 1)
                    // 清空输入框中的内容 EditText 的  setText()函数
                    inputText.setText("")
                }
            }
        }
    }

    //初始化几条数据
    private fun initMsg(){
        val msg1 = Msg("Hello guy", Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello. Who is that?", Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("This is Tom. Nice talking to you", Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}