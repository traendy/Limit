package de.soenke.limit.data.local.dao

import de.soenke.limit.data.Limit
import de.soenke.limit.data.source.LimitDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalLimitDataSource internal constructor(
    private val limitDao: LimitDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LimitDataSource {
    override suspend fun saveLimit(limit: Limit) = withContext(ioDispatcher) {
        limitDao.insert(limit)
    }

    override suspend fun updateLimit(limit: Limit) = withContext(ioDispatcher) {
        limitDao.insert(limit)
    }

    override suspend fun deleteLimit(limit: Limit) = withContext(ioDispatcher) {
        limitDao.deleteLimit(limit)
    }

    override suspend fun retrieveLimit(id: Int): Limit = withContext(ioDispatcher) {
        return@withContext limitDao.getLimitById(id)
    }

    override suspend fun retrieveAllLimits(): List<Limit> = withContext(ioDispatcher) {
        return@withContext limitDao.getAllLimits()
    }
}