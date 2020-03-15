package de.soenke.limit.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_table")
data class Payment(
    @ColumnInfo(name = "name")
    @NonNull
    val name:String,

    @ColumnInfo(name = "value")
    @NonNull
    val value: Float,

    @ColumnInfo(name = "timeStamp")
    @NonNull
    val timeStamp:Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int = 0
)