package com.example.cleanshelf.data.remote.Dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StkResponse(
    @Json(name = "CheckoutRequestID")
    val checkoutRequestID: String?,
    @Json(name = "CustomerMessage")
    val customerMessage: String?,
    @Json(name = "MerchantRequestID")
    val merchantRequestID: String?,
    @Json(name = "ResponseCode")
    val responseCode: Int?,
    @Json(name = "ResponseDescription")
    val responseDescription: String?
)