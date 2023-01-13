package com.example.onlineshop.data

import com.example.onlineshop.data.model.CommodityResponse
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.data.repository.remote.RemoteDataSource
import com.example.onlineshop.ui.model.Commodity
import com.example.onlineshop.util.Result
import javax.inject.Inject

class FakeRemoteDataSource @Inject constructor(): RemoteDataSource {
    override suspend fun loadCommodityItems(
        page: Int,
        pageSize: Int
    ): Result<List<CommodityResponse>> {
        return Result.Success(emptyList())
    }
}

class FakeRepository @Inject constructor(): CommodityRepository {
    override suspend fun getCommodityItems(page: Int): Result<List<Commodity>> {
        return Result.Success(
            generateFakeCommodities()
        )
    }

}

fun generateFakeCommodities(): List<Commodity> {
    val commodities = mutableListOf<Commodity>()
    for (i in 1..100) {
        commodities.add(
            Commodity(i, i * 200L + i, "title $i", "desc $i", 0)
        )
    }
    return commodities
}