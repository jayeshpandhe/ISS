package com.example.iss.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * ISS Db
 */
@Database(entities = [ISSPosition::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "iss_db"
    }
    abstract fun issPositionDao(): ISSPositionDao
}