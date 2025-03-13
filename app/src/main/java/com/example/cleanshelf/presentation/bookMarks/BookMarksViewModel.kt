package com.example.cleanshelf.presentation.bookMarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.PrimaryKey
import com.example.cleanshelf.data.local.BookMark
import com.example.cleanshelf.data.local.BookMarkDao
import com.example.cleanshelf.data.local.ProductEntity
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.homeScreen.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookMarksViewModel @Inject constructor(private val bookMarkDao: BookMarkDao) : ViewModel() {

    private val _bookMarkState: MutableStateFlow<BookMarkState> = MutableStateFlow(BookMarkState())
    val bookMarkState = _bookMarkState.asStateFlow()

    private val _isFavourite = MutableLiveData<Set<Int>>() // Store bookmarked IDs
    val isFavourite: LiveData<Set<Int>> get() = _isFavourite

    init {
        getAllBookMarks()
    }

    fun isBookMarked(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedProduct = bookMarkDao.getBookMArkById(productId)
            val updatedSet = _isFavourite.value.orEmpty().toMutableSet()

            if (savedProduct != null) {
                updatedSet.add(productId)
            } else {
                updatedSet.remove(productId)
            }

            _isFavourite.postValue(updatedSet)
        }
    }

    fun toggleButton(productResponseItem: ProductResponseItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingProduct = bookMarkDao.getBookMArkById(productResponseItem.id)

            if (existingProduct != null) {
                bookMarkDao.deleteBookMark(existingProduct)
            } else {
                val bookMark = BookMark(
                    id = productResponseItem.id,
                    name = productResponseItem.name,
                    image = productResponseItem.image,
                    description = productResponseItem.description,
                    unit = productResponseItem.unit,
                    price = productResponseItem.price,
                    category = productResponseItem.category
                )
                bookMarkDao.updateBookMarks(bookMark) // Make sure you have an `insertBookMark` method
            }

            getAllBookMarks() // Refresh bookmarks after update
        }
    }

    fun getAllBookMarks() {
        viewModelScope.launch(Dispatchers.IO) {
            bookMarkDao.getAllBookMarks().collect { bookMarks ->
                _bookMarkState.value = _bookMarkState.value.copy(
                    bookMarks = bookMarks
                )

                // Update set of bookmarked IDs
                _isFavourite.postValue(bookMarks.map { it.id }.toSet())
            }
        }
    }
}

data class BookMarkState(
    val bookMarks: List<BookMark>? = emptyList(),
)
