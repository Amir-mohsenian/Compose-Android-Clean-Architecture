package com.example.onlineshop.data.repository.remote

import com.example.onlineshop.data.model.CommodityResponse
import com.example.onlineshop.util.Result
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

interface RemoteDataSource {
    suspend fun loadCommodityItems(page: Int, pageSize: Int): Result<List<CommodityResponse>>
}

class RemoteDataSourceImp @Inject constructor(): RemoteDataSource {
    override suspend fun loadCommodityItems(page: Int, pageSize: Int): Result<List<CommodityResponse>> {
        return Result.Success(makeFakeCommodities(page, pageSize))
    }

    private suspend fun makeFakeCommodities(page: Int, pageSize: Int): List<CommodityResponse> {
        delay(2000L)
        val startingIndex = page * pageSize
        return if(startingIndex + pageSize <= 1000) {
                fakeData.slice(startingIndex until startingIndex + pageSize)
        } else emptyList()
    }

    private val fakeData = (1..1000).map {
        CommodityResponse(
            id = it,
            price = Random.nextLong(from = 5000L, until = 100000L),
            title = "Item $it",
            desc = "Description $it"
        )
    }
}