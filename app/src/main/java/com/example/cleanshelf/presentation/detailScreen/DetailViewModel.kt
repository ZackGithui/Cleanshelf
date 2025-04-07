package com.example.cleanshelf.presentation.detailScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Dto.ProductResponse
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
       private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()



    fun getProductsByIds(id: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(id).data
            _detailState.value= _detailState.value.copy(product=product)
            Log.d(TAG, "getProductsById: $product")

        }

    }

}


data class DetailState(
    val product: ProductResponseItem? = null
)