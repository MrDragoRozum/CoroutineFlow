package com.sumin.coroutineflow.lessons.lesson8

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val job1 = coroutineScope.launch {
        getFlow().first().let {
            println(it)
        }
    }
    val job2 = coroutineScope.launch {
        getFlow().collect {
            println(it)
        }
    }
    job1.join()
    job2.join()
}

fun getFlow(): Flow<Int> = flow {
    repeat(10) {
        println("Emitted: $it")
        emit(it)
        delay(1000)
    }
}