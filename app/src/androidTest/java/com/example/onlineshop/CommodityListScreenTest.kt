package com.example.onlineshop

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.onlineshop.data.generateFakeCommodities
import com.example.onlineshop.ui.commodity_list.CommodityListScreen
import com.example.onlineshop.ui.commodity_list.UiState
import com.example.onlineshop.ui.model.Commodity
import org.junit.Rule
import org.junit.Test

class CommodityListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun firstLoading() {
        val uiState = UiState(isLoading = true)
        composeTestRule.setContent {
            CommodityListScreen(uiState)
        }
        composeTestRule.onNodeWithTag("list_loading").assertIsDisplayed()
    }

    @Test
    fun showListAfterLoadingWhenItIsSuccess() {
        val items = generateFakeCommodities().slice(0..48)
        val uiState = UiState(isLoading = false, endReached = false, items = items)
        composeTestRule.setContent {
            CommodityListScreen(uiState)
        }
        composeTestRule.onNodeWithTag("list_loading").assertDoesNotExist()
        composeTestRule.onNodeWithTag("commodity_list").performScrollToIndex(5)
    }

    @Test
    fun showLoadingAndWaitToGetNewItems() {
        val items = generateFakeCommodities().slice(0..48)
        val uiState = UiState(isLoading = true, items = items)

        composeTestRule.setContent {
            CommodityListScreen(uiState = uiState)
        }

        composeTestRule.onNodeWithTag("commodity_list").performScrollToIndex(items.size - 1)


        composeTestRule.onNodeWithTag("list_loading").assertIsDisplayed()
        composeTestRule.onNodeWithTag("commodity_list").assertIsDisplayed()
    }

    @Test
    fun showQtyCorrectlyInCommdityItem() {
        composeTestRule.setContent {
            CommodityItem(commodity = Commodity(
                id = 324,
                price = 234324L,
                title = "item 1",
                desc = "desc 1",
                qty = 4
            )
            )
        }

        composeTestRule.onNodeWithText("4").assertIsDisplayed()
    }

    @Composable
    private fun CommodityListScreen(uiState: UiState) {
        CommodityListScreen(
            uiState = uiState,
            onClick = {},
            onAddQty = {},
            onRemoveQty = {},
            requestMoreData = {  }) {

        }
    }

    @Composable
    private fun CommodityItem(commodity: Commodity) {
        com.example.onlineshop.ui.commodity_list.CommodityItem(
            commodity = commodity,
            onClick = {},
            addQuantity = {},
            removeQuantity = {}
        )
    }
}


