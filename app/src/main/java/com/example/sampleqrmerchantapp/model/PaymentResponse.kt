package com.example.sampleqrmerchantapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
@Parcelize
data class PaymentResponse(
    val amount: String?,
    val businessName: String?,
    val message: String?,
    val orderId: String?,
    val response: String?,
    val status: String?,
    val txnDate: String?
) : Parcelable