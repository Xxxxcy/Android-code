package com.example.jetpacktest

import androidx.room.Entity
import androidx.room.PrimaryKey

//加上注解 声明为一个实体类
//Room 的 第一步
@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {

    //使用注解 声明为主键
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
