package com.example.sampleqrmerchantapp.network

sealed class NetworkResult<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data : T) : NetworkResult<T>(data = data)
    class Error<T>(message: String) : NetworkResult<T>(errorMessage = message)
    class Loading<T> : NetworkResult<T>()
}