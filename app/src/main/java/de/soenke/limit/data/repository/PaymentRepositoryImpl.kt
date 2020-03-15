package de.soenke.limit.data.repository

import de.soenke.limit.data.Payment
import de.soenke.limit.data.source.PaymentDataSource
import de.soenke.limit.di.ApplicationModule
import kotlinx.coroutines.*
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    @ApplicationModule.PaymentLocalDataSource private val paymentLocalDataSource: PaymentDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
):PaymentRepository {
    override suspend fun getAllPayments(): List<Payment> {
        return withContext(ioDispatcher){
            return@withContext paymentLocalDataSource.retrieveAllPayments()
        }
    }

    override suspend fun savePayment(payment: Payment) {
        coroutineScope {
            launch {
                paymentLocalDataSource.savePayment(payment)
            }
        }
    }

    override suspend fun deletePayment(payment: Payment) {
        coroutineScope {
            launch {
                paymentLocalDataSource.deletePayment(payment)
            }
        }
    }

    override suspend fun deleteAllPayments() {
        throw NotImplementedError()
    }
}