package com.example.cleanshelf.data.remote

import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.data.remote.Dto.StkRequest
import com.example.cleanshelf.data.remote.Dto.StkResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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

interface MpesaServices{
    @Headers("Content-Type: application/json","Authorization: Bearer YOUR_TOKEN")
    @POST("stkPush")
    suspend fun initiateStkPush(@Body request: StkRequest): Response<StkResponse>



}