package com.example.cleanshelf.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.CartApiService
import com.example.cleanshelf.data.remote.Dto.CartItem
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val api: CartApiService
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    private suspend fun getIdToken(): String? {
        return try {
            val user = FirebaseAuth.getInstance().currentUser
            user?.getIdToken(true)?.await()?.token
        } catch (e: Exception) {
            Log.e("CartViewModel", "Token fetch failed: ${e.message}", e)
            null
        }
    }

    fun getCartFromServer() {
        viewModelScope.launch {
            try {
                val token = getIdToken()
                val authHeader = token?.takeIf { it.isNotBlank() }?.let { "Bearer $it" }

                val products = api.getCart(authHeader) // token can be null or "Bearer <token>"
                _cartState.value = CartState(products)
                Log.d("CartViewModel", "Cart loaded with ${products.size} item(s)")
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error loading cart: ${e.message}", e)
            }
        }
    }



    fun addToCart(item: CartItem) {
        viewModelScope.launch {


                try {
                    val token = getIdToken()
                    val authHeader = token?.takeIf { it.isNotBlank() }?.let { "Bearer $it" }
                    api.addToCart(
                       authHeader,
                        item)
                    Log.d("CartViewModel", "Item added to cart")
                    getCartFromServer()
                } catch (e: Exception) {
                    Log.e("CartViewModel", "Error adding to cart: ${e.message}", e)
                }

        }
    }

    fun removeItemFromCart(item: CartItem) {
        viewModelScope.launch {
            val token = getIdToken()

                try {
                    api.clearCart(
                        "Bearer $token",
                        item.productId)
                    Log.d("CartViewModel", "Item removed from cart")
                    getCartFromServer()
                } catch (e: Exception) {
                    Log.e("CartViewModel", "Error removing item: ${e.message}", e)
                }

        }
    }
}

data class CartState(
    val products: List<CartItem> = emptyList()
) {
    val totalPrice: Int get() = products.sumOf { it.price * it.quantity }
}
