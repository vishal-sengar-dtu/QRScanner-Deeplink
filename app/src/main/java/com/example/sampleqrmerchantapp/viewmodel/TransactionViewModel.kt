package com.example.sampleqrmerchantapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleqrmerchantapp.model.PaymentRequest
import com.example.sampleqrmerchantapp.model.UIPaymentResponse
import com.example.sampleqrmerchantapp.network.NetworkResult
import com.example.sampleqrmerchantapp.repository.Repository
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: Repository) : ViewModel() {
    lateinit var deepLink : Uri
    private var orderId : String? = null
    var mid : String? = null
    var merchantName : String? = null
    var amount : String? = null
    var isDynamic = false

    private val _txnResponse = MutableLiveData<NetworkResult<UIPaymentResponse>>()
    val txnResponse: LiveData<NetworkResult<UIPaymentResponse>> get() = _txnResponse

    fun proceedTransaction() {
        _txnResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            val paymentRequest = PaymentRequest(
                mid = this@TransactionViewModel.mid,
                orderId = this@TransactionViewModel.orderId,
                amount = this@TransactionViewModel.amount
            )
//            val paymentRequest = PaymentRequest(
//                mid = "INTEGR77698636129383",
//                orderId = "globalTest_93720_20240926150526",
//                amount = this@TransactionViewModel.amount
//            )

            val response = repository.makePayment(paymentRequest)
            Log.d("Response", response.errorMessage.toString())
            _txnResponse.postValue(response)
        }
    }

    fun parseDeepLink() {
        val merchantName = deepLink.getQueryParameter("pn")
        val orderId = deepLink.getQueryParameter("tr")
        val amount = deepLink.getQueryParameter("am")

        Log.d("Deeplink Data", "$merchantName, $orderId, $amount")

        this.merchantName = merchantName
        //Dynamic QR Case
        if(orderId != null) {
            this.isDynamic = true
            this.orderId = orderId
            this.amount = amount
        }
    }
}