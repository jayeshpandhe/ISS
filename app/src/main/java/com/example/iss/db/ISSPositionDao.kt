package com.example.iss.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * ISS position data access interface
 */
@Dao
interface ISSPositionDao {
    @Query("SELECT * FROM iss_positions")
    suspend fun getAll(): List<ISSPosition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg issPosition: ISSPosition)

    @Query("SELECT * FROM iss_positions WHERE timeStamp = :timeStamp AND latitude = :latitude AND longitude = :longitude")
    suspend fun getISSPosition(timeStamp: Long, latitude: Double, longitude: Double): ISSPosition

    @Delete
    suspend fun delete(issPosition: ISSPosition)
}