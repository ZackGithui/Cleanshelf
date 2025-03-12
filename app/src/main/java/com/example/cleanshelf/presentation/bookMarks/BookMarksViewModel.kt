package com.example.cleanshelf.presentation.bookMarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanshelf.data.local.BookMark
import com.example.cleanshelf.data.local.BookMarkDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookMarksViewModel @Inject constructor(private val bookMarkDao: BookMarkDao) : ViewModel() {

    private val _bookMarkState: MutableStateFlow<BookMarkState> = MutableStateFlow(BookMarkState())
    val bookMarkState = _bookMarkState.asStateFlow()

    fun getAllBookMarks() {
        viewModelScope.launch {
            bookMarkDao.getAllBookMarks().collect { bookMarks ->
                _bookMarkState.value = _bookMarkState.value.copy(
                    bookMarks = bookMarks
                )
            }
        }
    }

    fun addBookMark(bookMark: BookMark){
        viewModelScope.launch {
            bookMarkDao.updateBookMarks(bookMark)
        }
    }

    fun deleteBookMark(bookMark: BookMark){
        viewModelScope.launch {
            bookMarkDao.deleteBookMark(bookMark)
        }
    }


}

data class BookMarkState(
    val bookMarks: List<BookMark>? = emptyList()
)