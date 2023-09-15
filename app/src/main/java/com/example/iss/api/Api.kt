package com.example.iss.api

import com.example.iss.model.ISS
import com.example.iss.model.AstronautsResponse
import retrofit2.http.GET

/**
 * Web APIs
 */
interface Api {
    companion object {
        const val BASE_URL = "http://api.open-notify.org/"
    }

    @GET("iss-now.json")
    suspend fun getISSPosition(): ISS

    @GET("astros.json")
    suspend fun getAstronauts(): AstronautsResponse
}