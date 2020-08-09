package com.sunnyweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.MainActivity
import com.sunnyweather.android.R
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    //通过懒加载来获取实例  允许我们在整个类中随时使用 并不用关心何时初始化和是否为空的前提
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java)}

    private lateinit var adapter : PlaceAdapter

    //加载fragment布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //先进行判断 是否储存城市数据 如果储存就取出并解析成Place对象 跳转传递到WeatherActivity
        //PlaceFragment嵌入MainActivity中且之前存在选中的城市才跳转 防止无线循环跳转
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        //先给 recyclerView 设置layoutManager 与 adapter适配器
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        //用placeList作为数据源
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        //监听搜索框的内容变化 引起搜索城市数据发起的请求
        searchPlaceEdit.addTextChangedListener { editable ->

            val content = editable.toString()

            if (content.isNotEmpty()) {
                //当内容发生变化 获取内容并传递给searchPlaces
                viewModel.searchPlaces(content)
            }else{
                //当搜索框为空的时候 recyclerView隐藏 将背景图显示出来
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //获取 服务器响应的数据  对placeLiveData进行观察
        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                //数据不为空 将数据加入 placeList 并刷新界面
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}