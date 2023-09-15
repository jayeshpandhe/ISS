package com.example.iss

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.iss.db.ISSPosition
import com.example.iss.model.ISS
import java.lang.Exception

/**
 * Utility class to manage locations
 */
class LocationUtils {
    companion object {
        fun locationPermissionGranted(context: Context) =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        fun showLocationOnGoogleMap(context: Context, location: Location) {
            val locationLabel = context.getString(R.string.iss_location)
            val gmmIntentUri =
                Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}($locationLabel)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            try {
                context.startActivity(mapIntent)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    context.getString(R.string.unable_to_view_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun toLocation(iss: ISS): Location {
            val issLocation = Location("")
            issLocation.latitude = iss.position.latitude
            issLocation.longitude = iss.position.longitude
            return issLocation
        }

        fun toLocation(issPosition: ISSPosition): Location {
            val issLocation = Location("")
            issLocation.latitude = issPosition.latitude
            issLocation.longitude = issPosition.longitude
            return issLocation
        }
    }
}