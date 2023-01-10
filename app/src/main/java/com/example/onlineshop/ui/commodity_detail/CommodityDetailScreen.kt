package com.example.onlineshop.ui.commodity_detail

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onlineshop.ui.commodity_list.CommodityItem
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel

@Composable
fun CommodityDetailScreen(modifier: Modifier = Modifier, viewModel: CommodityListViewModel) {
    val commodity by viewModel.detailCommodity.collectAsState()

    commodity?.let {
        var quantity by remember {
            mutableStateOf(it.getQuantity())
        }

        CommodityItem(
            commodity = it.copy(qty = quantity), onClick = {

            }, addQuantity = { cmd ->
                viewModel.addCommodity(cmd)
                quantity++
            }, removeQuantity = { cmd ->
                viewModel.removeCommodity(cmd)
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
    CommodityDetailScreen(viewModel = hiltViewModel())
}