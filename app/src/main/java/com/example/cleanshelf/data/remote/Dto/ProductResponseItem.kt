package com.example.cleanshelf.data.remote.Dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponseItem(
    @Json(name = "category")
    val category: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "number")
    val number: Int,
    @Json(name = "originalPrice")
    val price: Int,
    @Json(name = "discountedPrice")
    val discountedPrice: Double,
    @Json(name = "unit")
    val unit: String
)