package com.example.onlineshop.di

import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.data.repository.CommodityRepositoryImp
import com.example.onlineshop.data.repository.remote.RemoteDataSource
import com.example.onlineshop.data.repository.remote.RemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp): RemoteDataSource

    @Singleton
    @Binds
    abstract fun provideRepository(repositoryImp: CommodityRepositoryImp): CommodityRepository
}