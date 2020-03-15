package de.soenke.limit.data.repository

import de.soenke.limit.data.Payment

interface PaymentRepository {
    suspend fun getAllPayments():List<Payment>
    suspend fun savePayment(payment: Payment)
    suspend fun deletePayment(payment: Payment)
    suspend fun deleteAllPayments()
}