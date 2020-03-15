package de.soenke.limit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.soenke.limit.data.Limit
import de.soenke.limit.data.Payment
import de.soenke.limit.data.repository.LimitRepository
import de.soenke.limit.data.repository.PaymentRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val limitRepository: LimitRepository
) :
    ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()
    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> = _payments

    private val _paymentSum = MutableLiveData<Float>()
    val paymentSum: LiveData<Float> = _paymentSum

    private val _activeLimit = MutableLiveData<Limit?>()
    val activeLimit: LiveData<Limit?> = _activeLimit

    fun getActiveLimit() {
        _loading.value = true
        launch {
            val limit = limitRepository.getActiveLimit()
            _activeLimit.postValue(limit)
            if (limit != null) {
                // not yet implemented
            } else {

            }
        }
            .invokeOnCompletion { _loading.postValue(false) }
    }

    fun getPayments() {
        _loading.value = true
        launch {
            val payments = paymentRepository.getAllPayments()
            _payments.postValue(payments)
            if (payments.isNotEmpty()) {
                val sum = payments.map { it.value }.reduce { acc, payment -> acc + payment }
                _paymentSum.postValue(sum)
            } else {
                _paymentSum.postValue(0f)
            }
        }
            .invokeOnCompletion { _loading.postValue(false) }
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun addPayment(payment: Payment) {
        launch {
            paymentRepository.savePayment(payment)
        }
        getPayments()
    }

    fun deleteAll() {
        launch {
            paymentRepository.deleteAllPayments()
        }
    }

    fun delete(payment: Payment) {
        launch {
            paymentRepository.deletePayment(payment)
        }
        getPayments()
    }

    fun setLimit(limit: Limit) {
        _loading.postValue(true)
        if (activeLimit.value != null) {
            val temp = activeLimit.value
            temp!!.active = false
            launch {
                limitRepository.saveLimit(temp)
                limitRepository.saveLimit(limit)
            }.invokeOnCompletion {
                _activeLimit.postValue(limit)
                _loading.postValue(false)
            }
        } else {
            launch {
                limitRepository.saveLimit(limit)
            }.invokeOnCompletion {
                _activeLimit.postValue(limit)
                _loading.postValue(false)
            }
        }

    }
}