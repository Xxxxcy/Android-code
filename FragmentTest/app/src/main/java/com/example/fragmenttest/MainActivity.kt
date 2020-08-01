package com.example.fragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.left_fragment.*

//动态添加Fragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            //replaceFragment(AnotherRightFragment())
        }
        //replaceFragment(RightFragment())
    }

/*
    //private fun replaceFragment(fragment: Fragment){

        //获取FragmentManager
        val fragmentManager = supportFragmentManager
        //开启一个事务 beginTransaction
        val transaction = fragmentManager.beginTransaction()
        //添加 替换Fragment
        transaction.replace(R.id.rightFrag, fragment)
        //实现返回栈
        transaction.addToBackStack(null)
        //提交事务
        transaction.commit()
    }

 */
}