package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

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
            viewModel.plusOne()
        }

        clearBtn.setOnClickListener {
            viewModel.clear()
        }

        getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            //获取ID数据
            viewModel.getUser(userId)
        }

        //观察数据 并显示在TextView上
        viewModel.user.observe(this, Observer {
            user -> infoText.text = user.firstName
        })

        //调用getLifecycle 获得lifecycle 对象 然后通过addObserver 来观察生命周期
        //最后 将MyObserver() 实例传入
        //Activity 继承自AppCompatActivity() 或者 Fragment 继承自androidx.fragment.app.Fragment
        //则它本身就是一个LifecycleOwner的实例 Androidx库已经自动完成了
        //lifecycle.addObserver(MyObserver())

        //通过MainViewModel 中的 count 变成了 LiveData 对象
        //任何LivaData对象都可以调用 observe方法来观察 数据对象
        viewModel.counter.observe(this, Observer {
                count -> infoText.text = count.toString()
        })

        val userDao = AppDatabase.getDatabase(this).userDao()
        val user1 = User("Tom", "Brady", 40)
        val user2 = User("Tom", "Hanks", 63)

        addDataBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }

        updateDataBtn.setOnClickListener {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
            }
        }

        deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Brady")
            }
        }

        queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("MainActivity", user.toString())
                }
            }
        }

        doWorkBtn.setOnClickListener {

            //OneTimeWorkRequest 接受 SimpleWorker中返回的Class对象 创建一次后台任务请求
            //延时运行 添加标签
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .addTag("simple")
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build()
            //系统在合适的时间运行请求
            WorkManager.getInstance(this).enqueue(request)
        }
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }
    }

    private fun refershCounter() {

        infoText.text = viewModel.counter.toString()
    }

}