package com.example.sampleqrmerchantapp

import android.app.Application
import com.example.sampleqrmerchantapp.network.ApiClient
import com.example.sampleqrmerchantapp.network.ApiService
import com.example.sampleqrmerchantapp.repository.Repository

class SampleQRMerchantApplication : Application() {
    lateinit var repository: Repository
    private lateinit var apiService : ApiService

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        repository = Repository(ApiClient.instance)
    }
}