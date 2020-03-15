package de.soenke.limit.data.local.dao

import de.soenke.limit.data.Payment
import de.soenke.limit.data.source.PaymentDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalPaymentDataSource internal constructor(
    private val paymentDao: PaymentDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PaymentDataSource {
    override suspend fun savePayment(payment: Payment) = withContext(ioDispatcher) {
        paymentDao.insert(payment)
    }

    override suspend fun updatePayment(payment: Payment) = withContext(ioDispatcher) {
        paymentDao.insert(payment)
    }

    override suspend fun deletePayment(payment: Payment) = withContext(ioDispatcher) {
        paymentDao.deletePayment(payment)
    }

    override suspend fun retrievePayment(id: Int): Payment = withContext(ioDispatcher){
        return@withContext paymentDao.getPaymentById(id)
    }

    override suspend fun retrieveAllPayments(): List<Payment> = withContext(ioDispatcher) {
        return@withContext paymentDao.getAllPayments()
    }
}