package com.example.providertest

import kotlin.reflect.KProperty

/*
//实现一个自己的类 并实现Set接口
//MySet的构造函数中 接受了一个HashSet参数 相当于一个辅助对象
//后续方法实现 调用的都是 辅助对象的相应方法
class MySet<T>(val helperSet: HashSet<T>): Set<T> {
    override val size :Int
        get() = helperSet.size

    override fun contains(element: T) = helperSet.contains(element)

    override fun containsAll(elements: Collection<T>) = helperSet.containsAll(elements)

    override fun isEmpty() = helperSet.isEmpty()

    override fun iterator() = helperSet.iterator()
}
 */

/*
//通过委托  和上面 辅助对象一样效果
//要对某个方法 重新实现 直接重写那一个方法就行
class MySet<T>(val helperSet: HashSet<T>): Set<T> by helperSet {

    fun helloWorld() = println("Hello World")
    override fun isEmpty() = false
}

 */

//委托属性的语法结构
class MyClass {
    //对MyClass 的 p值赋值 和 获取值
    //会调用 委托类的 getValue  setValue
    var p by Delegate()
}

class Delegate{

    var propValue: Any? = null

    //重写方法 用operator
    //参数1：指定 使用的类 参数2：获取各种属性相关值 当前用不到
    operator fun getValue(myClass: MyClass , prop: KProperty<*>): Any?{
        return propValue
    }

    //参数3： 赋予的值
    operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: Any?){
        propValue = value
    }
}