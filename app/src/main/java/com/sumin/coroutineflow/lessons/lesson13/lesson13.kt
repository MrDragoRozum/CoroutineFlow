package com.sumin.coroutineflow.lessons.lesson13

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        val flow: Flow<Int> = flow {
            repeat(10) {
                println("Emitted: $it")
                emit(it)
                println("After emit: $it")
                delay(200)
            }
        }.buffer(-1)
        flow.collect {
            println("\nCollected: $it\n")
            delay(1000)
        }
    }
    job.join()
}