package com.sumin.coroutineflow.lessons.lesson16

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry

suspend fun main() {
    loadDataFlow()
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }
        .retry { true }
        .catch { emit(State.Error) }
        .collect {
          when(it) {
              is State.Content -> {
                  println("Collected: ${it.value}")
              }
              State.Loading -> {
                  println("Loading...")
              }
              State.Error -> println("Something went wrong")
          }
        }
}

fun loadDataFlow(): Flow<Int> = flow {
    repeat(5) {
        delay(500)
        emit(it)
    }
    throw RuntimeException("Исключение из Flow")
}

sealed class State {
    data class Content(val value: Int) : State()
    object Loading : State()
    object Error : State()
}