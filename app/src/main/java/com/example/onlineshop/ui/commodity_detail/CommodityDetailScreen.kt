package com.example.onlineshop.ui.commodity_detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel

@Composable
fun CommodityDetailScreen(modifier: Modifier = Modifier, viewModel: CommodityListViewModel) {
    val commodity by viewModel.detailCommodity.collectAsState()

    Text(text = "commodity is ${commodity?.title}")
}