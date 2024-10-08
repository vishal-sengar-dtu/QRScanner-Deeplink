package com.example.sampleqrmerchantapp.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UIPaymentResponse(
    val amount: String?,
    val businessName: String?,
    val message: String?,
    val orderId: String?,
    val response: String?,
    val status: String?,
    val txnDate: String?,
    val mid: String?
) : Parcelable