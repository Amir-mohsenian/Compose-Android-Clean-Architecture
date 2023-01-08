package com.example.onlineshop.data.network

interface Paginator {
    suspend fun loadNextItems()
    fun refreshItems()
}