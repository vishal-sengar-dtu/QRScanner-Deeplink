package com.example.sampleqrmerchantapp.repository

import android.util.Log
import com.example.sampleqrmerchantapp.model.TxnStatusRequestBody
import com.example.sampleqrmerchantapp.model.TxnStatusResponse
import com.example.sampleqrmerchantapp.network.ApiClient
import com.example.sampleqrmerchantapp.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository {
    suspend fun getTxnResponse(txnStatusRequestBody: TxnStatusRequestBody) : NetworkResult<TxnStatusResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.instance.getTxnResponse(txnStatusRequestBody)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("Transaction Status", it.toString())
                        return@withContext NetworkResult.Success(it)
                    } ?: run {
                        return@withContext NetworkResult.Error("Empty response body")
                    }
                } else {
                    return@withContext NetworkResult.Error("Http error: ${response.code()} ${response.message()}")
                }
            }
            catch(e : Exception) {
                return@withContext NetworkResult.Error("Network error: ${e.message}")
            }
        }
    }
}