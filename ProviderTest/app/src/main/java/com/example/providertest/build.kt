package com.example.providertest

//高阶函数
//build 实现和 apply 相同的功能
fun <T> T.build(block: T.() -> Unit): T{
    block()
    return this
}