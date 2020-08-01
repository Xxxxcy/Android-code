package com.example.broadcastbestpractice

fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int{
    val result = operation(num1, num2)
    return result
}

//定义了一个StringBuilder类的build扩展函数
fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder{
    block()
    return this
}

fun plus(num1: Int, num2: Int): Int{
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int{
    return num1 - num2
}

fun main(){
    /*
    val num1 = 100
    val num2 = 80
    //val result1 = num1AndNum2(num1, num2, ::plus)
    val result1 = num1AndNum2(num1, num2){n1, n2 -> n1 + n2}
    //val result2 = num1AndNum2(num1, num2, ::minus)
    val result2 = num1AndNum2(num1, num2){n1, n2 -> n1 - n2}

    println("result is $result1")
    println("result is $result2")

     */

    val list = listOf("apple", "banana", "pear", "grape")
    val result = StringBuilder().build {
        append("Strat eating fruits.\n")
        for (fruit in list){
            append(fruit).append("\n")
        }
        append("Ate all fruits.")
    }
    println(result.toString())
}