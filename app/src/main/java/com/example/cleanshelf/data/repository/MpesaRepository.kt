package com.example.cleanshelf.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cleanshelf.data.remote.Dto.StkRequest
import com.example.cleanshelf.data.remote.Dto.StkResponse
import com.example.cleanshelf.data.remote.MpesaServices
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MpesaRepository @Inject constructor(private val api: MpesaServices) {

    suspend fun initiatePayments(request: StkRequest): StkResponse? {
        return try {
            val response = api.initiateStkPush(request)
            if (response.isSuccessful) {
                Log.d("MpesaaRepository", "initiatePayments: Success")
                Log.d(TAG, "initiatePayments: ${response.errorBody()?.toString()}")
                response.body()  // Properly returning the body
            } else {
                Log.e("MpesacRepository", "Error: ${response.raw()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MpesayRepository", "Exception: ${e.localizedMessage}")
            null
        }
    }
}