package com.example.cleanshelf.data.remote.Dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StkRequest(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "phone")
    val phone: String
)