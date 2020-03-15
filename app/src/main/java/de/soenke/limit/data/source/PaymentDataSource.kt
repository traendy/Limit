package de.soenke.limit.data.source

import de.soenke.limit.data.Payment

interface PaymentDataSource {
    suspend fun savePayment(payment: Payment)
    suspend fun updatePayment(payment: Payment)
    suspend fun deletePayment(payment: Payment)
    suspend fun retrievePayment(id:Int):Payment
    suspend fun retrieveAllPayments():List<Payment>
}