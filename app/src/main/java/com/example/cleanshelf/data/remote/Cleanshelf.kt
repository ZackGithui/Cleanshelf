package com.example.cleanshelf.data.remote

import com.example.cleanshelf.data.remote.Dto.CartItem
import com.example.cleanshelf.data.remote.Dto.OrderItemItem
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.data.remote.Dto.StkRequest
import com.example.cleanshelf.data.remote.Dto.StkResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Cleanshelf {

    @GET("products")
    suspend fun getAllProducts(
        //@Header("Authorization") token: String
    ): List<ProductResponseItem>

   @GET("products/{category}")
    suspend fun getProductsOnCategory(
        @Query("category") category: String
    ): List<ProductResponseItem>

    @GET("product/{id}")
    suspend fun getProductById(
        @Path ("id") id : Int,
       // @Header("Authorization") token: String
    ): ProductResponseItem









}

interface MpesaServices{
    @Headers("Content-Type: application/json","Authorization: Bearer YOUR_TOKEN")
    @POST("stkPush")
    suspend fun initiateStkPush(@Body request: StkRequest): Response<StkResponse>



}

interface CartApiService{
    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: String? = null
    ): List<CartItem>

    @POST("add")
    suspend fun addToCart(
        @Header("Authorization") token: String? = null,
        @Body item: CartItem)

    @DELETE("clear/{itemId}")
    suspend fun clearCart(
        @Header("Authorization") token: String? = null,
                          @Path("itemId") productId: Int)
}


interface OrderApiService{
    @POST("place")
    suspend fun placeOrder(@Header("Authorization") token: String): Response<ResponseBody>


    @GET("orders")
    suspend fun getOrders(@Header("Authorization")token : String): Response<List<OrderItemItem>>
}