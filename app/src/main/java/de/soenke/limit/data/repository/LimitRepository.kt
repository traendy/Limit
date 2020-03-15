package de.soenke.limit.data.repository

import de.soenke.limit.data.Limit

interface LimitRepository {
    suspend fun getAllLimits(): List<Limit>
    suspend fun saveLimit(limit: Limit)
    suspend fun deleteLimit(limit: Limit)
    suspend fun deleteAllLimits()
    suspend fun getActiveLimit(): Limit?
}