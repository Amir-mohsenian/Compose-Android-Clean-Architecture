package com.example.onlineshop.ui.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Commodity(
    val id: Int,
    val price: Long,
    val title: String,
    val desc: String,
    var qty: Int
) {
    var qtyState by mutableStateOf(qty)
}
