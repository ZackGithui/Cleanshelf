package com.example.cleanshelf.util

sealed class Resource<T>(
    val data: T? = null,
    var message: String = ""
) {
    class Error<T>(message: String) : Resource<T>(message = message)
    class Success<T>(data: T?) : Resource<T>(data = data)
}