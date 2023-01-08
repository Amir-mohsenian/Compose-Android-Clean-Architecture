package com.example.onlineshop.data.repository

import com.example.onlineshop.data.repository.remote.RemoteDataSource
import com.example.onlineshop.ui.model.Commodity
import com.example.onlineshop.util.Result
import javax.inject.Inject

private const val PAGE_SIZE = 50

interface CommodityRepository {
    suspend fun getCommodityItems(page: Int): Result<List<Commodity>>
}

class CommodityRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CommodityRepository {
    override suspend fun getCommodityItems(page: Int): Result<List<Commodity>> {
        val result = remoteDataSource.loadCommodityItems(page, PAGE_SIZE)
        return when (result) {
            is Result.Success -> {
                Result.Success(
                    result.data.map { it.toCommodity() }
                )
            }
            is Result.Loading -> Result.Loading
            is Result.Error -> Result.Error(result.errorMessage)
        }
    }
}