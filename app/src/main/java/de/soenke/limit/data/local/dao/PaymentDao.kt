package de.soenke.limit.data.local.dao

import androidx.room.*
import de.soenke.limit.data.Payment

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: Payment)

    @Query("DELETE FROM payment_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM payment_table ORDER BY timeStamp DESC")
    suspend fun getAllPayments(): List<Payment>

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * FROM payment_table WHERE id = :paymentId LIMIT 1")
    fun getPaymentById(paymentId: Int):Payment
}