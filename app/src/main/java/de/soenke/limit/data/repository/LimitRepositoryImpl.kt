package de.soenke.limit.data.repository

import de.soenke.limit.data.Limit
import de.soenke.limit.data.source.LimitDataSource
import de.soenke.limit.di.ApplicationModule
import kotlinx.coroutines.*
import javax.inject.Inject

class LimitRepositoryImpl @Inject constructor(
    @ApplicationModule.LimitLocalDataSource private val limitLocalDataSource: LimitDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LimitRepository {
    override suspend fun getAllLimits(): List<Limit> {
        return withContext(ioDispatcher) {
            return@withContext limitLocalDataSource.retrieveAllLimits()
        }
    }

    override suspend fun saveLimit(limit: Limit) {
        coroutineScope {
            launch {
                limitLocalDataSource.saveLimit(limit)
            }
        }
    }

    override suspend fun deleteLimit(limit: Limit) {
        coroutineScope {
            launch {
                limitLocalDataSource.deleteLimit(limit)
            }
        }
    }

    override suspend fun deleteAllLimits() {
        throw NotImplementedError()
    }

    override suspend fun getActiveLimit(): Limit? {
        return withContext(ioDispatcher) {
            val limitList = limitLocalDataSource.retrieveAllLimits().filter { it.active }
            if(limitList.isEmpty()){
                return@withContext null
            }else{
                return@withContext limitList.first()
            }
        }
    }
}