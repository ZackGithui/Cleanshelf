package com.example.cleanshelf.data.repository

import android.util.Log
import com.example.cleanshelf.data.remote.Dto.OrderItemItem
import com.example.cleanshelf.data.remote.OrderApiService
import com.example.cleanshelf.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val orderApi: OrderApiService,

    ) {

    suspend fun placeOrder(): Result<Any> {
        val token = firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
        return try {

            val response = orderApi.placeOrder("Bearer $token")
            if (response.isSuccessful) {
                Log.d("Order", "placeOrder: successs $response")
                Result.success(response.body() ?: "Order placed successfully")

            } else {
                Log.d("Order", "placeOrder: not successful ${response.message()}")
                Result.failure(Exception(response.errorBody()?.string() ?: "Error placing order"))

            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun getOrder(): Resource<List<OrderItemItem>> {
        val token = firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
        if (token.isNullOrBlank()) return Resource.Error("No token")

        return try {
            val response = orderApi.getOrders("Bearer $token")

            Timber.d("HTTP ${response.code()} ${response.message()}")
            Timber.d("Response body: ${response.body()}")
            Timber.d("Response error: ${response.errorBody()?.string()}")

            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                Resource.Success(data)
            } else {
                Resource.Error("Backend error: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception calling getOrders")
            Resource.Error("Exception: cv ${e.localizedMessage}")
        }
    }

}
