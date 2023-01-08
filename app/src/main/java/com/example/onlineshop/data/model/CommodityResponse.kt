package com.example.onlineshop.data.model

import com.example.onlineshop.ui.model.Commodity

class CommodityResponse(
    val id: Int,
    val price: Long,
    val title: String,
    val desc: String,
) {
    fun toCommodity() = Commodity(
        id = id,
        price = price,
        title = title,
        desc = desc,
        qty = 0
    )
}