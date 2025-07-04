package com.example.cleanshelf.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Dto.OrderItemItem
import com.example.cleanshelf.data.remote.OrderApiService
import com.example.cleanshelf.data.repository.OrderRepository
import com.example.cleanshelf.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository: OrderRepository,
   ) :
    ViewModel() {

    private val _orderSate: MutableStateFlow<OrderState> = MutableStateFlow(OrderState())
    val orderState = _orderSate.asStateFlow()
    fun placeOrder(onResult: (Result<Any>) -> Unit) {
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            val idToken = user?.getIdToken(false)?.await()?.token

            if (idToken != null) {
                val result = orderRepository.placeOrder()
                onResult(result)
            } else {
                onResult(Result.failure(Exception("User not authenticated")))
            }
        }
    }

    init {
        getOrder()
    }

    fun getOrder() {
        _orderSate.value = _orderSate.value.copy(loading = true)

        viewModelScope.launch {
            val result = orderRepository.getOrder()

            when (result) {
                is Resource.Success -> {
                    Timber.d("Fetched orders: ${result.data}")
                    _orderSate.value = _orderSate.value.copy(
                        loading = false,
                        error = "",
                        data = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Timber.e("Error fetching orders: ${result.message}")
                    _orderSate.value = _orderSate.value.copy(
                        loading = false,
                        error = result.message ?: "Unknown error"
                    )
                }
            }
        }
    }

}

data class OrderState(
    val loading: Boolean = false,
    val error: String = "",
    val data: List<OrderItemItem> = emptyList()
)
