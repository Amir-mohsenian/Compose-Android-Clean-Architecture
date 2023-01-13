package com.example.onlineshop.ui.commodity_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onlineshop.R
import com.example.onlineshop.ui.model.Commodity
import com.example.onlineshop.ui.theme.*

@Composable
fun CommodityListRoute(
    modifier: Modifier = Modifier,
    viewModel: CommodityListViewModel,
    onCommodityClick: (Commodity) -> Unit
) {
    val uiState = viewModel.uiState

    CommodityListScreen(
        uiState = uiState,
        onClick = onCommodityClick,
        onAddQty = { viewModel.addCommodity(it) },
        onRemoveQty = { viewModel.removeCommodity(it) },
        requestMoreData = { viewModel.loadMoreCommodity() },
        submitCommodities = { viewModel.proceedCommodities() }
    )
}

@Composable
fun CommodityListScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onClick: (Commodity) -> Unit,
    onAddQty: (Commodity) -> Unit,
    onRemoveQty: (Commodity) -> Unit,
    requestMoreData: () -> Unit,
    submitCommodities: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!uiState.isLoading && uiState.items.isEmpty()) {
            EmptyList(modifier = Modifier.padding(top = 24.dp))
            return@Column
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .testTag("commodity_list"),
            contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp)
        ) {
            items(uiState.items.size, key = { uiState.items[it].id }) { i ->
                if (i >= uiState.items.size - 1 && !uiState.endReached && !uiState.isLoading) {
                    requestMoreData()
                }

                CommodityItem(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp),
                    commodity = uiState.items[i],
                    onClick = {
                        onClick(it)
                    },
                    addQuantity = {
                        onAddQty(it)
                    },
                    removeQuantity = {
                        onRemoveQty(it)
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
                        CircularProgressIndicator(
                            color = Green500,
                            modifier = Modifier.testTag("list_loading")
                        )
                    }
                }
            }
        }

        ExpenseBottomItem(
            modifier = Modifier.fillMaxWidth(),
            totalExpense = uiState.totalExpense,
            hasError = uiState.minExpenseError
        )

        Button(shape = MaterialTheme.shapes.large,
            enabled = !uiState.minExpenseError && uiState.onSaleTime,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                submitCommodities()
            }) {
            Text(
                text = stringResource(id = R.string.title_checking_out),
                color = if (!uiState.onSaleTime || uiState.minExpenseError) Color.Gray else Color.White,
                style = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.ShoppingBasket,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = stringResource(id = R.string.title_empty_list),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.h3
        )
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(commodity)
            }, elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = commodity.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = commodity.desc,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Filled.PriceChange, contentDescription = null)
                Text(
                    text = commodity.price.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            Row(
                modifier = Modifier
                    .size(width = 150.dp, height = 70.dp)
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Red700,
                    modifier = Modifier
                        .testTag("plus")
                        .size(36.dp)
                        .clickable {
                            addQuantity(commodity)
                        })

                Text(
                    text = commodity.getQuantity().toString(),
                    style = MaterialTheme.typography.h6,
                    color = Green500,
                    modifier = Modifier.testTag("quantity_value")
                )

                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = null,
                    tint = Red700,
                    modifier = Modifier
                        .testTag("minus")
                        .size(36.dp)
                        .clickable {
                            removeQuantity(commodity)
                        })
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
    Text(
        text = stringResource(id = R.string.title_total_expense, totalExpense),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = if (hasError) Red500 else Green500
            )
            .wrapContentHeight(align = Alignment.CenterVertically)

    )
}

@Preview
@Composable
fun CommodityListScreenPreview() {
    OnlineShopTheme {
        Surface {
            CommodityListRoute(viewModel = hiltViewModel(), onCommodityClick = {})
        }
    }
}