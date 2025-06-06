package com.example.cleanshelf.data.remote

import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Cleanshelf {

    @GET("products")
    suspend fun getAllProducts(): List<ProductResponseItem>

    @GET("products")
    suspend fun getProductsOnCategory(
        @Query("category") category: String
    ): List<ProductResponseItem>

    @GET("product/{id}")
    suspend fun getProductById(
        @Path ("id") id : Int
    ): List<ProductResponseItem>


}