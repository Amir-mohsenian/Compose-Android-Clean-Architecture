package com.example.onlineshop.di

import com.example.onlineshop.data.FakeRemoteDataSource
import com.example.onlineshop.data.FakeRepository
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.data.repository.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class RepositoryTestModule {
    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(fakeRemoteDataSource: FakeRemoteDataSource): RemoteDataSource

    @Singleton
    @Binds
    abstract fun provideRepository(fakeRepository: FakeRepository): CommodityRepository
}