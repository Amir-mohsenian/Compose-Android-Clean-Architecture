package com.example.onlineshop.ui.commodity_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.network.DefaultPagintor
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.ui.model.Commodity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val MIN_BUY = 500000L

@HiltViewModel
class CommodityListViewModel @Inject constructor(
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
    private set

    private val paginator = DefaultPagintor(
        initialKey = uiState.page,
        onLoadUpdated = {
            uiState = uiState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            commodityRepository.getCommodityItems(nextPage)
        },
        getNextKey = {
            uiState.page + 1
        },
        onError = { errorMessage ->
            uiState = uiState.copy(error = errorMessage)
        },
        onSuccess = { items, newKey ->
            uiState = uiState.copy(
                items = uiState.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadMoreCommodity()
    }

    fun addCommodity(commodity: Commodity) {
        val items = uiState.items
        items.find { it.id == commodity.id }?.apply {
            qtyState++
        }
        uiState = uiState.copy(
            items = items
        )

        val lastExpense = uiState.totalExpense
        val totalExpense = lastExpense + commodity.price

        uiState = uiState.copy(
            items = items,
            totalExpense = totalExpense,
            minExpenseError = totalExpense < MIN_BUY,
            onSaleTime = onSaleTime()
        )
    }

    fun removeCommodity(commodity: Commodity) {
        if (commodity.qtyState == 0) {
            return
        }

        val items = uiState.items
        items.find { it.id == commodity.id }?.apply {
            qtyState--
        }

        val lastExpense = uiState.totalExpense
        val totalExpense = lastExpense - commodity.price

        uiState = uiState.copy(
            items = items,
            totalExpense = totalExpense,
            minExpenseError = totalExpense < MIN_BUY,
            onSaleTime = onSaleTime()
        )
    }

    fun proceedCommodities() {

    }

    fun loadMoreCommodity() {
        viewModelScope.launch {
            paginator.loadNextItems()
            uiState = uiState.copy(onSaleTime = onSaleTime())
        }
    }

    private fun onSaleTime(): Boolean {
        val hour =
            Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran")).get(Calendar.HOUR_OF_DAY)
        return hour in 8..19
    }

}

data class UiState(
    val isLoading: Boolean = false,
    val items: List<Commodity> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val totalExpense: Long = 0,
    val minExpenseError: Boolean = true,
    val onSaleTime: Boolean = false
)