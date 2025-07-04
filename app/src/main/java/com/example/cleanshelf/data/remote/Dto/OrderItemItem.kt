package com.example.cleanshelf.data.remote.Dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderItemItem(
    @Json(name = "id")
    val id: Int?,

    @Json(name = "quantity")
    val quantity: String?,

    @Json(name = "price")
    val price: Int?,

    @Json(name = "productId")
    val productId: Int?,

    @Json(name = "orderId")
    val orderId: String?,
    @Json(name = "productImage")
    val image: String?,
    @Json(name = "productName")
    val name: String?,

    @Json(name = "userId")
    val userId: String?,

    @Json(name = "orderDate")
    val createdAt: String? // This field comes directly from your backend DTO
)
