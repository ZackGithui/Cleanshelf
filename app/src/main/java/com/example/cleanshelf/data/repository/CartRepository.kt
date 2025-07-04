package com.example.cleanshelf.data.repository

import com.example.cleanshelf.data.remote.CartApiService
import com.example.cleanshelf.data.remote.Dto.CartItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val apiService: CartApiService,
    private val firebaseAuth: FirebaseAuth
) {
    private suspend fun getAuthTokenOrNull(): String? {
        val user = firebaseAuth.currentUser
        return user?.getIdToken(false)?.await()?.token?.let { "Bearer $it" }
    }

    suspend fun fetchCart(): List<CartItem> {
        val token = getAuthTokenOrNull()
        return apiService.getCart(token)
    }

    suspend fun addToCart(item: CartItem) {
        val token = getAuthTokenOrNull()
        apiService.addToCart(token, item)
    }

    suspend fun clearCart(itemId: Int) {
        val token = getAuthTokenOrNull()
            ?: throw IllegalStateException("Only authenticated users can clear the cart")
        apiService.clearCart(token, itemId)
    }
}
