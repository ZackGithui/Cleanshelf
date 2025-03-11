package com.example.cleanshelf.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    fun onEvents(event: SearchEvents) {
        when (event) {
            is SearchEvents.QueryChanged -> {
                updateSearchQuery(event.query)
            }
        }
    }


    private fun updateSearchQuery(query: String) {

        viewModelScope.launch {
            delay(500)
            try {
                val products = repository.getAllProducts().data.orEmpty()
                val filteredProducts =
                    products.filter { it.name.lowercase().contains(query.lowercase()) }

                _searchState.value = _searchState.value.copy(
                    query = query,
                    result = filteredProducts,
                    error = null.toString()

                )
            } catch (e: Exception) {
                _searchState.value = _searchState.value.copy(
                    error = "Error fetching products ${e.message}"
                )

            }
        }

    }
}


data class SearchState(
    val result: List<ProductResponseItem> = emptyList(),
    val query: String = "",
    var error: String = ""
)

//Search Screen events
sealed interface SearchEvents {
    data class QueryChanged(val query: String) : SearchEvents
}