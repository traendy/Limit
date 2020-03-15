package de.soenke.limit.data.source

import de.soenke.limit.data.Limit

interface LimitDataSource {
    suspend fun saveLimit(limit: Limit)
    suspend fun updateLimit(limit: Limit)
    suspend fun deleteLimit(limit: Limit)
    suspend fun retrieveLimit(id: Int): Limit
    suspend fun retrieveAllLimits(): List<Limit>
}