package com.example.retrofittest

import kotlinx.coroutines.*

//开启协程
/*
fun main() {

    //创建的是 顶层协程
    GlobalScope.launch {
        println("codes run in coroutines scope")
        //非阻塞式的挂起函数 只挂起当前协程 不会影响其他协程
        delay(1500)
        println("codes run in coroutines scope finished")
    }
    //让主线程 阻塞1S
    //该方法 阻塞当前线程 该线程下的所有携程都被堵塞
    Thread.sleep(1000)
}

 */

/*
fun main() {
    //创建一个协程的作用域 保证协程作用域内所有代码和子协程没有全部执行完之前一直堵塞当前线程
    runBlocking {
        println("codes run in coroutines scope")
        delay(1500)
        println("codes run in coroutines scope finished")
    }

}

 */

/*
//创建多个协程
fun main(){

    runBlocking {

        //launch 必须在协程的作用域中才可以调用
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }

        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
    }
}

 */

/*
fun main() {

    val start = System.currentTimeMillis()

    runBlocking {
        //循环创建1W个协程 打印一个.
        repeat(10000){
            launch {
                println(".")
            }
        }
    }
    val end = System.currentTimeMillis()
    //输出耗时
    println(end - start)
}

 */

/*
//coroutineScope 可以将当前协程阻塞 只有 当其作用域内的 代码和 子协程都执行完毕以后 它之后的代码才会运行
fun main() {
    //创建一个协程作用域
    //会阻塞当前线程
    runBlocking {
        //创建一个子协程作用域
        //只会阻塞当前协程 不会影响其他线程 和 协程
        coroutineScope {
            //创建一个子协程
            launch {

                for (i in 1..10) {

                    println(i)
                    delay(1000)
                }
            }
        }
        println("coroutineScope finished")
    }
    println("runBlocking finished")
}

 */

//launch 返回的是一个job对象
//在协程中获取代码结果-> await()
fun main() {

    //await 方法会将当前协程阻塞,直到获得执行结果
    runBlocking {

        val start = System.currentTimeMillis()

        val result1 = async {
            delay(1000)
            5 + 5
        }

        val result2 = async {
            delay(1000)
            4 + 6
        }

        //2个async为 串行关系 效率低下
        //println("result is ${result1 + result2}")

        //并行关系 同时进行
        println("result is ${result1.await() + result2.await()}")

        val end = System.currentTimeMillis()
        println("cost ${end - start}")
    }
}