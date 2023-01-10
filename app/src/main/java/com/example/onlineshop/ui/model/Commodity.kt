package com.example.onlineshop.ui.model

data class Commodity(
    val id: Int,
    val price: Long,
    val title: String,
    val desc: String,
    private var qty: Int
) {
    fun addQuantity() {
        qty++
    }

    fun removeQuantity() {
        qty--
    }

    fun getQuantity() = qty
}