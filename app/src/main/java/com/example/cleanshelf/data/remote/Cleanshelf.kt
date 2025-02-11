package com.example.cleanshelf.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface Cleanshelf {

    @GET("products")
    suspend fun getAllProducts()

    @GET("products")
    suspend fun getProductsOnCategory(
        @Query("category") category: String
    )

    @GET("product")
    suspend fun getProductById(
        @Query("id") id: Int
    )


}