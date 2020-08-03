package com.example.jetpacktest

import androidx.room.*

//编写 Dao Room中 对数据库操作进行封装 Room 第二步

//添加Dao注解 Room会识别成一个Dao
@Dao
interface UserDao {

    //添加注解  会把参数插入表中 并自动生成主键返回
    //插入 更新 删除 不需要写SQL 直接添加注解就行
    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(newUser: User)

    //对其中进行查找数据 需要写SQL语句
    @Query("select * from User")
    fun loadAllUsers(): List<User>

    @Query("select * from User where age > :age")
    fun loadUsersOlderThan(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    //非实体类参数 来增删查改数据 也要写SQL语句
    @Query("delete from User where lastName = :lastName")
    fun deleteUserByLastName(lastName: String): Int

}