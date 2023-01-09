package com.example.onlineshop.util

import com.example.onlineshop.data.model.CommodityResponse
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.data.repository.remote.RemoteDataSource
import com.example.onlineshop.ui.model.Commodity
import kotlin.random.Random

object FakeRemoteDataSource: RemoteDataSource {
    override suspend fun loadCommodityItems(
        page: Int,
        pageSize: Int
    ): Result<List<CommodityResponse>> {
        return Result.Success(makeFakeCommodities(page, pageSize))
    }

    private fun makeFakeCommodities(page: Int, pageSize: Int): List<CommodityResponse> {
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= 50) {
            fakeData.slice(startingIndex until startingIndex + pageSize)
        } else emptyList()
    }

    private val fakeData = (1..50).map {
        CommodityResponse(
            id = it,
            price = Random.nextLong(from = 5000L, until = 100000L),
            title = "Item $it",
            desc = "Description $it"
        )
    }
}
