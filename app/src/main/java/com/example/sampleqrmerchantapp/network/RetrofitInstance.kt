package com.example.sampleqrmerchantapp.network

import com.google.gson.Gson

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val CONNECTION_TIMEOUT: Long = 60
    private const val READ_TIMEOUT: Long = 60
    private const val WRITE_TIMEOUT: Long = 60
    private const val BASE_URL = "https://securestage.paytmpayments.com"

    val instance: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }

    private val retrofitInstance: Retrofit = Retrofit.Builder().client(getOKHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()


    private fun getOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}