package com.example.cleanshelf.presentation.homeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewmodel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val _HomeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val HomeScreenState = _HomeScreenState.asStateFlow()


    init {
        getAllProducts()
    }


    fun getAllProducts() {
        viewModelScope.launch {
            try {


                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = true,
                    error = ""
                )
                val products = repository.getAllProducts().data ?: emptyList()
                Log.d(TAG, "getAllProductsVM: $products")

                val groupedByCategory = products.groupBy { it.category.trim().lowercase() }
                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = false,
                    all = products,
                    productByCategory = groupedByCategory,
                    error = "",


                    )
                Log.d(TAG, "getAllProducts: ${_HomeScreenState.value.all}")
            } catch (e: Exception) {
                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error occurred"
                )
            }

        }
    }

    fun getProductsByCategory(category: String): List<ProductResponseItem> {
        return _HomeScreenState.value.productByCategory[category.lowercase()] ?: emptyList()
    }

    fun onEvents(event: Events) {
        when (event) {
            is Events.CategoryChanged -> {
                _HomeScreenState.value = _HomeScreenState.value.copy(
                    category = event.category
                )
            }
        }
    }


}


data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String? = "",
    val productByCategory: Map<String, List<ProductResponseItem>> = emptyMap(),
    val all: List<ProductResponseItem> = emptyList(),
    val category: String = ""
)

sealed class Events {
    data class CategoryChanged(val category: String) : Events()
}