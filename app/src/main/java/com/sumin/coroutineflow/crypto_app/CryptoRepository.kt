package com.sumin.coroutineflow.crypto_app

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

object CryptoRepository {

    private val currencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val currencyList = mutableListOf<Currency>()

//    suspend fun getCurrencyList(): List<Currency> {
//        delay(3000)
//        generateCurrencyList()
//        return currencyList.toList()
//    }

    fun getCurrencyList(): Flow<List<Currency>> = flow {
        while(true) {
            generateCurrencyList()
            delay(3000)
            emit(currencyList.toList())
            delay(3000)
        }
    }
    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(currencyNames.size) {
                add(Random.nextInt(1000, 2000))
            }
        }
        val newData = buildList {
            for ((index, currencyName) in currencyNames.withIndex()) {
                val price = prices[index]
                val currency = Currency(name = currencyName, price = price)
                add(currency)
            }
        }
        currencyList.clear()
        currencyList.addAll(newData)
    }
}