package com.sumin.coroutineflow.lessons.lesson9

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {

    val flow = MutableSharedFlow<Int>()

    coroutineScope.launch {
        repeat(5) {
            println("Emitted: $it")
            flow.emit(it)
            delay(1000)
        }
    }

    val job1 = coroutineScope.launch {
        flow.collect {
            println("Got from 1st collector: $it")
        }
    }
    delay(5000)
    val job2 = coroutineScope.launch {
        flow.collect {
            println("Got from 2nd collector: $it")
        }
    }

    val job3 = coroutineScope.launch {
        delay(5000)
        job1.cancel()
        job2.cancel()
    }

    job3.join()
}

fun getFlow(): Flow<Int> = flow {
    repeat(5) {
        println("Emitted: $it")
        emit(it)
        delay(1000)
    }
}