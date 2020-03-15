package de.soenke.limit.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "limit_table")
data class Limit(
    @ColumnInfo(name = "name")
    @NonNull
    val name: String,

    @ColumnInfo(name = "value")
    @NonNull
    val value: Float,

    @ColumnInfo(name = "timeStampBegin")
    @NonNull
    val timeStampBegin: Long,

    @ColumnInfo(name = "timeStampEnd")
    @NonNull
    val timeStampEnd: Long,

    @ColumnInfo(name = "active")
    @NonNull
    var active: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int = 0
)