package com.example.materialtest

import java.lang.RuntimeException

fun <T: Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty")
    var maxNum = nums[0]
    for (num in nums){
        if (num > maxNum){
            maxNum = num
        }
    }
    return maxNum
}

fun main(){

    val a = 10
    val b = 15
    val c = 5

    val largest = max(a, b, c)
    println(largest)
}
