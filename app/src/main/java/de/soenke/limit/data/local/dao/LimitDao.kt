package de.soenke.limit.data.local.dao

import androidx.room.*
import de.soenke.limit.data.Limit

@Dao
interface LimitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(limit: Limit)

    @Query("DELETE FROM limit_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM limit_table ORDER BY timeStampBegin ASC")
    suspend fun getAllLimits(): List<Limit>

    @Delete
    suspend fun deleteLimit(limit: Limit)

    @Query("SELECT * FROM limit_table WHERE id = :limitId LIMIT 1")
    fun getLimitById(limitId: Int): Limit
}