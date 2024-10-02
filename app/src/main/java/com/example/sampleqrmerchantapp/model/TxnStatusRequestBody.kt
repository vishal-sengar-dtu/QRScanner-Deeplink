package com.example.sampleqrmerchantapp.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
@Parcelize
data class TxnStatusRequestBody(
    val amount: String?,
    val mid: String?,
    val orderId: String?
) : Parcelable