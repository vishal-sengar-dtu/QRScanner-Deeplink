package com.example.sampleqrmerchantapp.network

sealed class NetworkResult<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data : T) : NetworkResult<T>(data = data)
    class Error<T>(data : T?, message: String) : NetworkResult<T>(data = null, errorMessage = message)
    class Loading<T> : NetworkResult<T>()
}