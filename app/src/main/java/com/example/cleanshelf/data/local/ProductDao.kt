package com.example.cleanshelf.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Upsert
    suspend fun updateProducts(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProducts(productEntity: ProductEntity)

    @Query("select * from ProductEntity")
    fun getAllProducts(): Flow<List<ProductEntity>>
}