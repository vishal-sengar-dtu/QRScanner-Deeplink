package com.example.sampleqrmerchantapp.network

import com.example.sampleqrmerchantapp.model.TxnStatusResponse
import com.example.sampleqrmerchantapp.model.TxnStatusRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers

interface ApiService {
    @Headers("CONNECT_TIMEOUT:5000", "READ_TIMEOUT:5000", "WRITE_TIMEOUT:5000")
    @GET("mockbank/upi/psp/qr/payment")
    suspend fun getTxnResponse(
        @Body troubleshootRequest: TxnStatusRequestBody
    ): Response<TxnStatusResponse>

}