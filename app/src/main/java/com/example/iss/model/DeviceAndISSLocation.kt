package com.example.iss.model

import android.location.Location

data class DeviceAndISSLocation(
    val deviceLocation: Location,
    val issLocation: ISS
)