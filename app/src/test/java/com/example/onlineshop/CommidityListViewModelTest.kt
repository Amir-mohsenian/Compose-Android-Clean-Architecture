package com.example.onlineshop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.onlineshop.data.repository.CommodityRepositoryImp
import com.example.onlineshop.ui.commodity_list.CommodityListViewModel
import com.example.onlineshop.util.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommodityListViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repository = CommodityRepositoryImp(FakeRemoteDataSource)
    private lateinit var viewModel: CommodityListViewModel

    @Before
    fun setup() {
        viewModel = CommodityListViewModel(repository)
    }

    @Test
    fun `load items when viewmodel is initialized`() = runTest {
        advanceUntilIdle()
        Assert.assertEquals(viewModel.uiState.items.size, 50)
    }

    @Test
    fun `add commodity expense when it is added`() = runTest {
        advanceUntilIdle()
        val commodity = viewModel.uiState.items[0]
        viewModel.addCommodity(commodity)
        Assert.assertEquals(viewModel.uiState.totalExpense, commodity.price)
        val commodity1 = viewModel.uiState.items[1]
        viewModel.addCommodity(commodity1)
        Assert.assertEquals(viewModel.uiState.totalExpense, commodity.price + commodity1.price)
    }

    @Test
    fun `not remove commodity when quantity is zero`() = runTest {
        advanceUntilIdle()
        val commodity = viewModel.uiState.items[0]
        viewModel.removeCommodity(commodity)
        Assert.assertEquals(viewModel.uiState.totalExpense, 0L)
    }

    @Test
    fun `decrease expense when commodity is removed`() = runTest {
        advanceUntilIdle()
        val commodity = viewModel.uiState.items[0]
        val commodity1 = viewModel.uiState.items[1]
        viewModel.addCommodity(commodity)
        viewModel.addCommodity(commodity1)

        viewModel.removeCommodity(commodity)
        Assert.assertEquals(viewModel.uiState.totalExpense, commodity1.price)
    }

    @Test
    fun `make proceed enable when total expense more than 500000`() = runTest {
        advanceUntilIdle()
        Assert.assertEquals(viewModel.uiState.minExpenseError, true)

        val commodity = viewModel.uiState.items[0].copy(price = 499000)
        viewModel.addCommodity(commodity)
        Assert.assertEquals(viewModel.uiState.minExpenseError, true)

        val commodity1 = viewModel.uiState.items[0].copy(price = 2000)
        viewModel.addCommodity(commodity1)
        Assert.assertEquals(viewModel.uiState.minExpenseError, false)
    }

    @Test
    fun `check order time when viewmodel is initialized`()  {
        Assert.assertEquals(viewModel.uiState.onSaleTime, false)
    }

    @Test
    fun `next page must be increase when page is updated`() = runTest {
        viewModel.loadMoreCommodity()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.uiState.page, 2)

        viewModel.loadMoreCommodity()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.uiState.page, 3)
    }
}