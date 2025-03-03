package com.example.cleanshelf.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.repository.ProductsRepository
import com.example.cleanshelf.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val api: Cleanshelf) : ProductsRepository {
    override suspend fun getAllProducts(): Resource<List<ProductResponseItem>> =
        try {
            val products = api.getAllProducts()
            Resource.Success(products)
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")

        } catch (e: IOException) {
            Resource.Error("Server error occurred")
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred")
        }


    override suspend fun getProductsByCategory(category: String): Resource<List<ProductResponseItem>> =
        try {
            val products = api.getProductsOnCategory(category)
            Resource.Success(products)

        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error("Server error occurred")
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred")
        }

    override suspend fun getProductById(id: Int): Resource<List<ProductResponseItem>> =
        try {
            Log.d(TAG, "Fetching product with ID: $id")

            val product = api.getProductById(id)

            Log.d(TAG, "Fetched Product: $product") // Log response

            Resource.Success(product)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP Exception: ${e.message}")
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred")
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}")
            Resource.Error("Server error occurred")
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            Resource.Error("An unknown error occurred")
        }


}