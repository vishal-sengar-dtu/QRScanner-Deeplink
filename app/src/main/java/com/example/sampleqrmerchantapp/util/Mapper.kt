package com.example.sampleqrmerchantapp.util

import com.example.sampleqrmerchantapp.model.PaymentResponse
import com.example.sampleqrmerchantapp.model.UIPaymentResponse

object Mapper {
    fun toUIPaymentResponse(paymentResponse: PaymentResponse, mid: String) : UIPaymentResponse {
        return UIPaymentResponse(
            amount =  paymentResponse.amount ,
            businessName = paymentResponse.businessName,
            message = paymentResponse.message,
            orderId = paymentResponse.orderId,
            response = paymentResponse.response,
            status = paymentResponse.status,
            txnDate = paymentResponse.txnDate,
            mid = mid
        )
    }
}