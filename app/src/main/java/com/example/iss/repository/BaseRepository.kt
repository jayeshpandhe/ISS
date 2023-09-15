package com.example.iss.repository

import com.example.iss.api.Api
import com.example.iss.db.ISSPosition
import com.example.iss.db.ISSPositionDao
import com.example.iss.model.ISS

/**
 * Make web API and DB calls to provide various ISS info
 */
class BaseRepository(
    private val api: Api,
    private val issPositionDao: ISSPositionDao
) {
    /**
     * Fetches ISS position from web API and stores it into the db
     */
    suspend fun getISS(): ISS {
        val iss = api.getISSPosition()
        insertISS(iss)
        return iss
    }

    /**
     * Fetches astronauts list from web API
     */
    suspend fun getAstronauts() = api.getAstronauts()

    /**
     * Fetches ISS position history data from db
     */
    suspend fun getISSPositionsHistory() = issPositionDao.getAll()

    suspend fun getISSPosition(timeStamp: Long, latitude: Double, longitude: Double) =
        issPositionDao.getISSPosition(timeStamp, latitude, longitude)

    private suspend fun insertISS(iss: ISS) {
        val issPosition = ISSPosition(
            latitude = iss.position.latitude,
            longitude = iss.position.longitude,
            timeStamp = iss.timeStamp
        )
        insertISSPosition(issPosition)
    }

    /**
     * Inserts ISS position data to the db
     */
    private suspend fun insertISSPosition(issPosition: ISSPosition) =
        issPositionDao.insert(issPosition)

    suspend fun deleteISSPosition(issPosition: ISSPosition) {
        issPositionDao.delete(issPosition)
    }
}