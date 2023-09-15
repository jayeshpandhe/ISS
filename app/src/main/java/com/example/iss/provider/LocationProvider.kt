package com.example.iss.provider

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

/**
 * Provides device location
 */
class LocationProvider(private val context: Context) {
    companion object {
        const val LOCATION_INTERVAL_MILLIS = 5000L
        const val TAG = "LocationProvider"
    }

    private val fusedLocationProviderClient = if (locationEnabled()) {
        LocationServices.getFusedLocationProviderClient(context)
    } else {
        null
    }

    private lateinit var locationCallback: LocationCallback

    /**
     * Requests device location using FusedLocationProviderClient and receives update every
     * LOCATION_INTERVAL_MILLIS.
     */
    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(callback: (Location) -> Unit) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                callback(location)
            }
        }
        fusedLocationProviderClient?.requestLocationUpdates(
            getLocationRequest(),
            locationCallback,
            context.mainLooper
        )
    }

    fun removeLocationUpdates() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    private fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = LOCATION_INTERVAL_MILLIS
        return locationRequest
    }

    private fun locationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.let {
            return it.isProviderEnabled(LocationManager.GPS_PROVIDER) || it.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        } ?: run {
            return false
        }
    }
}