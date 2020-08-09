package com.sunnyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*


//建立一个RecyclerView的适配器  P184

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    //定义一个内部类 且向主构函数中传入一个View参数 通常是RecyclerView的外项布局
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //获取内部的 TextView实例
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        //创建一个 ViewHolder 实例传入布局 然后返回
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,
        parent, false)
        
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {

            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity

            //对 PlaceFragment 所处的 Activity进行判断
            if (activity is WeatherActivity) {
                //关闭滑动菜单 赋值 并刷新天气信息
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()

            }else{
                //否则保持不变
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.viewModel.savePlace(place)
                //fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.savePlace(place)
        }
        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //对 RecyclerView 的子项数据进行赋值
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    //返回 RecyclerView 一共有多少子项
    override fun getItemCount() = placeList.size
}
