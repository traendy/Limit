package de.soenke.limit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import de.soenke.limit.data.Limit
import de.soenke.limit.data.Payment
import de.soenke.limit.data.local.dao.PaymentDao
import de.soenke.limit.data.local.dao.LimitDao


@Database(
    entities = [
        Payment::class,
        Limit::class
            ],
    version = 2,
    exportSchema = false
)
abstract class PaymentDataBase :RoomDatabase(){
    abstract fun paymentDao(): PaymentDao
    abstract fun limitDao():LimitDao
}