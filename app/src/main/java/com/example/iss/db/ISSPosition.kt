package com.example.iss.db

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * ISS position db table
 */
@Entity(
    tableName = "iss_positions",
    primaryKeys = ["timestamp", "latitude", "longitude"]
)
data class ISSPosition(
    @ColumnInfo(name = "timestamp")
    val timeStamp: Long,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,
)