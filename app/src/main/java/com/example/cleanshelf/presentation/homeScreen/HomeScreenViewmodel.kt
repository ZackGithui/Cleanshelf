package com.example.cleanshelf.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewmodel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {
    private val _HomeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val HomeScreenState = _HomeScreenState.asStateFlow()


    init {
        getAllProducts()
    }


    private fun getAllProducts() {
        viewModelScope.launch {
            try {


                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = true
                )
                val products = repository.getAllProducts().data
                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = false,
                    bakery = products!!.filter { it.category.lowercase() == "bakery" },
                    pantryStaples = products.filter { it.category.lowercase() == "pantry staples" },
                    beverages = products.filter { it.category.lowercase() == "beverages" },
                    freshProducts = products.filter { it.category.lowercase() == "fresh products" },
                    dairyProducts = products.filter { it.category.lowercase() == "dairy products" },
                    frozenFood = products.filter { it.category.lowercase() == "frozen food" },
                    cleaning = products.filter { it.category.lowercase() == "cleaning" },
                    error = ""

                )
            }
            catch (e: Exception){
                _HomeScreenState.value = _HomeScreenState.value.copy(
                    isLoading = false,
                    error = repository.getAllProducts().message ?: e.localizedMessage
                )
            }
        }
    }

}


data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val bakery: List<ProductResponseItem> = emptyList(),
    val pantryStaples: List<ProductResponseItem> = emptyList(),
    val beverages: List<ProductResponseItem> = emptyList(),
    val freshProducts: List<ProductResponseItem> = emptyList(),
    val dairyProducts: List<ProductResponseItem> = emptyList(),
    val frozenFood: List<ProductResponseItem> = emptyList(),
    val cleaning: List<ProductResponseItem> = emptyList()
)