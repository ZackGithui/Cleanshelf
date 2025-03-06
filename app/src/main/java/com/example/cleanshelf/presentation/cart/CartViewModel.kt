package com.example.cleanshelf.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.local.ProductDao
import com.example.cleanshelf.data.local.ProductEntity
import com.example.cleanshelf.domain.cart.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor( private val productDao: ProductDao): ViewModel() {

    private val _cartState: MutableStateFlow<CartState> = MutableStateFlow(CartState())

    val cartState = _cartState.asStateFlow()


    fun getCartProducts(){
        viewModelScope.launch {
            productDao.getAllProducts().collect{products->
                _cartState.value= _cartState.value.copy(
                    products= products
                )

            }
        }
    }

    fun addProductToCart(productEntity: ProductEntity){
        viewModelScope.launch {
            productDao.updateProducts(productEntity)
        }
    }

    fun removeProductFromCart(productEntity: ProductEntity){
        viewModelScope.launch {
            productDao.deleteProducts(productEntity)
        }
    }


}


data class CartState(
    val products : List<ProductEntity> ? = emptyList()
){
    val totalPrice:Int get() = products?.sumOf { it.price * it.quantity } ?: 0
}