package com.example.onlineshop.ui.commodity_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlineshop.ui.model.Commodity
import com.example.onlineshop.ui.theme.OnlineShopTheme

@Composable
fun CommodityListScreen(
    modifier: Modifier = Modifier,
    viewModel: CommodityListViewModel,
    onCommodityClick: (Commodity) -> Unit
) {
    val uiState = viewModel.uiState
    Column {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.items.size, key = { uiState.items[it].id }) { i ->
                if (i >= uiState.items.size - 1 && !uiState.endReached && !uiState.isLoading) {
                    viewModel.loadMoreCommodity()
                }

                CommodityItem(
                    modifier = Modifier.fillMaxWidth(),
                    commodity = uiState.items[i],
                    onClick = {
                        onCommodityClick(it)
                    },
                    addQuantity = {
                        viewModel.addCommodity(it)
                    },
                    removeQuantity = {
                        viewModel.removeCommodity(it)
                    }
                )
            }

            item {
                if (uiState.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        ExpenseBottomItem(
            modifier = Modifier.fillMaxWidth(),
            totalExpense = uiState.totalExpense,
            hasError = uiState.minExpenseError
        )

        Button(
            enabled = !uiState.minExpenseError && uiState.onSaleTime,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                viewModel.proceedCommodities()
            }) {
            Text(text = "Checking out", color = MaterialTheme.colors.primaryVariant)
        }
    }
}

@Composable
fun CommodityItem(
    modifier: Modifier = Modifier,
    commodity: Commodity,
    onClick: (Commodity) -> (Unit),
    addQuantity: (Commodity) -> (Unit),
    removeQuantity: (Commodity) -> (Unit)
) {
    Card(modifier = modifier.clickable {
        onClick(commodity)
    }) {
        Column {
            Text(
                text = commodity.title
            )

            Text(
                text = commodity.desc
            )

            Text(
                text = commodity.price.toString()
            )

            Row {
                Button(onClick = { addQuantity(commodity) }) {
                    Text(text = "ADD")
                }

                Text(text = commodity.qty.toString())

                Button(onClick = { removeQuantity(commodity) }) {
                    Text(text = "REMOVE")
                }
            }
        }
    }
}

@Composable
fun ExpenseBottomItem(
    modifier: Modifier = Modifier,
    totalExpense: Long,
    hasError: Boolean
) {
    Card(
        modifier = modifier.height(48.dp), backgroundColor =
        if (hasError) Color.Red else Color.Gray
    ) {
        Text(text = "Expense is : $totalExpense", textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun CommodityListScreenPreview() {
    OnlineShopTheme {
        Surface {

        }
    }
}