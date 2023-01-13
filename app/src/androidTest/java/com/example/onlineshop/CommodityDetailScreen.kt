package com.example.onlineshop

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.onlineshop.ui.commodity_detail.CommodityDetailScreen
import com.example.onlineshop.ui.model.Commodity
import org.junit.Rule
import org.junit.Test

class CommodityDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showCorrectQuantity() {
        composeTestRule.setContent {
            CommodityDetailScreen(
                commodity = commodity,
                onAddQuantity = { commodity.addQuantity() },
                onRemoveQuantity = {commodity.removeQuantity()}
            )
        }

        composeTestRule.onNodeWithText("4").assertIsDisplayed()
        composeTestRule.onNodeWithTag("plus").performClick()
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
        composeTestRule.onNodeWithTag("minus").performClick()
        composeTestRule.onNodeWithText("4").assertIsDisplayed()
    }
}

private val commodity = Commodity(
    id = 434, 3424324L, "title 1", "desc 1", 4
)