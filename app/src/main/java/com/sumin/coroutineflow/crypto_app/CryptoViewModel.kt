package com.sumin.coroutineflow.crypto_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {


    private val repository = CryptoRepository

    private val sharedFlowLoading = MutableSharedFlow<State>()

    val state = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { State.Loading }
        .mergeWith(sharedFlowLoading)

    private fun <T> Flow<T>.mergeWith(another: Flow<T>) = merge(this, another)
    fun refreshList() {
        viewModelScope.launch {
            sharedFlowLoading.emit(State.Loading)
            repository.refreshList()
        }
    }
}
