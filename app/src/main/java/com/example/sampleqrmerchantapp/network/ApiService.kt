package com.example.sampleqrmerchantapp.network

import com.example.sampleqrmerchantapp.model.PaymentResponse
import com.example.sampleqrmerchantapp.model.PaymentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("mockbank/upi/psp/qr/payment")
    suspend fun makePayment( @Body request: PaymentRequest): Response<PaymentResponse>
}