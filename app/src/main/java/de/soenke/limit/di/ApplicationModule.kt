package de.soenke.limit.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.soenke.limit.data.local.PaymentDataBase
import de.soenke.limit.data.local.dao.LocalLimitDataSource
import de.soenke.limit.data.local.dao.LocalPaymentDataSource
import de.soenke.limit.data.repository.LimitRepository
import de.soenke.limit.data.repository.LimitRepositoryImpl
import de.soenke.limit.data.repository.PaymentRepository
import de.soenke.limit.data.repository.PaymentRepositoryImpl
import de.soenke.limit.data.source.LimitDataSource
import de.soenke.limit.data.source.PaymentDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module (includes = [ApplicationModuleBinds::class])
object ApplicationModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PaymentLocalDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LimitLocalDataSource

    @JvmStatic
    @Singleton
    @PaymentLocalDataSource
    @Provides
    fun provideLocalPaymentDataSource(database: PaymentDataBase, ioDispatcher: CoroutineDispatcher):PaymentDataSource{
        return LocalPaymentDataSource(database.paymentDao(), ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @LimitLocalDataSource
    @Provides
    fun provideLocalLimitDataSource(
        database: PaymentDataBase,
        ioDispatcher: CoroutineDispatcher
    ): LimitDataSource {
        return LocalLimitDataSource(database.limitDao(), ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(context: Context) : PaymentDataBase{
        return Room.databaseBuilder(context.applicationContext, PaymentDataBase::class.java, "Payment.db").fallbackToDestructiveMigration().build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindPaymentRepository(repo: PaymentRepositoryImpl): PaymentRepository

    @Singleton
    @Binds
    abstract fun bindLimitRepository(repo: LimitRepositoryImpl): LimitRepository
}