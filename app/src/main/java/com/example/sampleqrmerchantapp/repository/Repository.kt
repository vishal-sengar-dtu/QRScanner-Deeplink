package com.example.sampleqrmerchantapp.repository

import android.util.Log
import com.example.sampleqrmerchantapp.util.Mapper
import com.example.sampleqrmerchantapp.model.PaymentRequest
import com.example.sampleqrmerchantapp.model.UIPaymentResponse
import com.example.sampleqrmerchantapp.network.ApiService
import com.example.sampleqrmerchantapp.network.NetworkResult
import java.lang.Exception

class Repository(private val apiService : ApiService) {
    suspend fun makePayment(paymentRequest: PaymentRequest) : NetworkResult<UIPaymentResponse> {
        return try {
                val response = apiService.makePayment(paymentRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("Transaction Response", it.toString())
                        val uiResponse = Mapper.toUIPaymentResponse(it, paymentRequest.mid.toString())
                        return NetworkResult.Success(uiResponse)
                    } ?: run {
                        return NetworkResult.Error(null, "Empty response body")
                    }
                } else {
                    return NetworkResult.Error(null, "Http error: ${response.code()} ${response.body().toString()}")
                }
            }
            catch(e : Exception) {
                return NetworkResult.Error(null, "Network error: ${e.message}")
            }
    }
}