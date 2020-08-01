package com.example.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    //创建一个水果集合 存放实例
    val fruits = mutableListOf(Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango))

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //将 TOOLBAR 传入 让外观功能和 ActionBar（标题栏）一样
        setSupportActionBar(toolbar)

        //在左侧加入一个导航按钮
        //getSupportActionBar得到 ActionBar实例  ActionBar通过ToolBar 实现
        supportActionBar?.let {

            //显示按钮并显示图标
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        //将Call菜单 默认选中
        navView.setCheckedItem(R.id.navCall)
        //设置事件监听器
        navView.setNavigationItemSelectedListener {
            //关闭滑动窗口
            drawerLayout.closeDrawers()
            //返回true表示事件被处理
            true
        }

        fab.setOnClickListener{ view ->
            // Snackbar交互式按钮
            //make方法 创建一个Snackbar对象
            Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo"){
                    Toast.makeText(this, "FAB clicked" ,Toast.LENGTH_SHORT).show()
                }.show()
        }

        initFruits()
        //第一个是Context 第二个是列数
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        recyclerView.adapter = adapter

        //设置下拉条的颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        //设置下拉刷新的监听器
        swipeRefresh.setOnRefreshListener {
            //进行本地刷新
            refreshFruits(adapter)
        }
    }

    //加载布局文件
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    //处理点击按钮事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.backup -> Toast.makeText(this, "You clicked Backup",
            Toast.LENGTH_SHORT).show()

            R.id.delete -> Toast.makeText(this, "You clicked delete",
                Toast.LENGTH_SHORT).show()

            R.id.settings -> Toast.makeText(this, "You clicked settings",
                Toast.LENGTH_SHORT).show()

            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }

        return true
    }

    //情况列表 并随机添加50个数据
    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {

        thread {
            //创建一个线程 使其沉睡2S 防止刷新太快
            Thread.sleep(2000)
            //切回主线程 刷新数据
            runOnUiThread {

                initFruits()
                adapter.notifyDataSetChanged()
                //刷新事件结束 隐藏刷新条
                swipeRefresh.isRefreshing = false
            }
        }
    }
}