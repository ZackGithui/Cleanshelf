package com.example.cleanshelf.data.remote.Dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartItem(
    @Json(name = "id")
    val id: String,
    @Json(name = "productId")
    val productId: Int,
    @Json(name = "quantity")
    val quantity: Int,
    @Json(name = "userId")
    val userId: String?,
    @Json(name = "price")
    val price: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String

)