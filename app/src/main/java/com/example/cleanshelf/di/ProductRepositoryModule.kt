package com.example.cleanshelf.di

import com.example.cleanshelf.data.repository.ProductsRepositoryImpl
import com.example.cleanshelf.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {
    @Binds
    @Singleton
    abstract fun productsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository
}