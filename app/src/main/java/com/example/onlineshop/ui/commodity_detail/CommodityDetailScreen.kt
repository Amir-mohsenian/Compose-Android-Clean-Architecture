package com.example.onlineshop.ui.commodity_detail

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onlineshop.ui.commodity_list.CommodityItem
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel
import com.example.onlineshop.ui.model.Commodity

@Composable
fun CommodityDetailScreen(
    modifier: Modifier = Modifier,
    commodity: Commodity?,
    onAddQuantity: (Commodity) -> Unit,
    onRemoveQuantity: (Commodity) -> Unit
) {

    commodity?.let {
        var quantity by remember {
            mutableStateOf(it.getQuantity())
        }

        CommodityItem(
            commodity = it.copy(qty = quantity), onClick = {

            }, addQuantity = { cmd ->
                onAddQuantity(cmd)
                quantity++
            }, removeQuantity = { cmd ->
                onRemoveQuantity(cmd)
                if (quantity <= 0) {
                    return@CommodityItem
                }
                quantity--
            })
    }
}

@Preview
@Composable
fun CommodityDetailScreenPreview() {
    CommodityDetailScreen(
        commodity = Commodity(223, 3423432L, "title 1", "desc 1", qty = 4),
        onAddQuantity = {},
        onRemoveQuantity = {})
}