package com.example.activity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/*
//将一个类 序列化
class Person : Serializable {
    var name = ""
    var age = 0
}

 */

/*
//使用 Parcelable 将一个完整的对象分解 分解后的每一部分都是Intent支持传递的数据类型
//先 实现接口
class Person() : Parcelable {
    var name = ""
    var age = 0

    //必须重写 2个方法
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name) // 写出name
        parcel.writeInt(age) // 写出age
    }

    override fun describeContents(): Int {
        return 0
    }

    //必须提供一个 名为CREATOR的匿名类 同时创建一个接口实现 且 重写2个方法
    companion object CREATOR : Parcelable.Creator<Person> {

        override fun createFromParcel(parcel: Parcel): Person {
            //创建一个对象 读取刚才写出的字段
            val person = Person()
            person.name = parcel.readString() ?: "" // 读取name
            person.age = parcel.readInt() // 读取age
            return person
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}

 */

//kotlin 提供了一个用法 但是需要将 传递的数据 封装在对象的主构函数中
@Parcelize
class Person(var name: String, var age: Int) : Parcelable