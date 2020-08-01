package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

//手机在旋转的时候 Activity会重建 并且不会保留数据
//通过ViewModel来解决
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //获取 SharedPreferences 实例 获得之前保存的计数
        sp = getPreferences(Context.MODE_PRIVATE)
        //如果 没有读到值 则设置0
        val countReserved = sp.getInt("count_reserved", 0)

        //1：通过 ViewModelProviders 来获取ViewModel的实例
        //在onCreate中创建实例 之后每次都会创建实例 就不会保存数据

        //2：在ViewModelProviders 的 of()方法中 传入MainViewModelFactory 参数
        //将读到的计数值传给了 MainViewModelFactory的构造函数 实现传递效果
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(countReserved))
                    .get(MainViewModel::class.java)

        plueOneBtn.setOnClickListener {
            viewModel.counter++
            refershCounter()
        }

        clearBtn.setOnClickListener {
            viewModel.counter = 0
            refershCounter()
        }

        refershCounter()

        //调用getLifecycle 获得lifecycle 对象 然后通过addObserver 来观察生命周期
        //最后 将MyObserver() 实例传入
        //Activity 继承自AppCompatActivity() 或者 Fragment 继承自androidx.fragment.app.Fragment
        //则它本身就是一个LifecycleOwner的实例 Androidx库已经自动完成了
        lifecycle.addObserver(MyObserver())
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter)
        }
    }

    private fun refershCounter() {

        infoText.text = viewModel.counter.toString()
    }

}