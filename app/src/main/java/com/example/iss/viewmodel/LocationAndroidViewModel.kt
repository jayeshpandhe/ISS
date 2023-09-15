package com.example.iss.viewmodel

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.iss.model.DeviceAndISSLocation
import com.example.iss.model.ISS
import com.example.iss.provider.LocationProvider
import com.example.iss.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Provides device and ISS location to the view
 */
@HiltViewModel
class LocationAndroidViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
    application: Application
) : AndroidViewModel(application) {
    companion object {
        const val TAG = "LocationAndroidViewModel"

        fun metersToMiles(distanceInMeters: Float) = distanceInMeters * 0.000621371

        fun calculateDistanceInMeters(source: Location, destination: Location) =
            source.distanceTo(destination)
    }

    private val _deviceAndISSLocationLiveData = MutableLiveData<DeviceAndISSLocation>()
    val deviceAndISSLocationLiveData: LiveData<DeviceAndISSLocation>
        get() = _deviceAndISSLocationLiveData

    private val locationProvider by lazy {
        LocationProvider(getApplication())
    }

    /**
     * Requests device location. It receives a location update and queries ISS position after
     * every LocationProvider.LOCATION_INTERVAL_MILLIS interval.
     */
    fun requestDeviceAndISSLocationUpdates() {
        locationProvider.requestLocationUpdates { location ->
            CoroutineScope(Dispatchers.IO).launch {
                val iss = baseRepository.getISS()
                val deviceAndISSLocation = DeviceAndISSLocation(location, iss)
                _deviceAndISSLocationLiveData.postValue(deviceAndISSLocation)
            }
        }
    }

    /**
     * Removes device location updates.
     */
    fun removeDeviceAndISSLocationUpdates() {
        locationProvider.removeLocationUpdates()
    }

    fun calculateDistanceInMiles(source: Location, destination: Location): Double {
        val distanceInMeters = calculateDistanceInMeters(source, destination)
        return metersToMiles(distanceInMeters)
    }
}