package com.example.cleanshelf.domain.repository

import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.util.Resource

interface ProductsRepository {
    suspend fun getAllProducts(): Resource<List<ProductResponseItem>>

    suspend fun getProductsByCategory(category: String): Resource<List<ProductResponseItem>>

    suspend fun getProductById(id: Int): Resource<ProductResponseItem>
}