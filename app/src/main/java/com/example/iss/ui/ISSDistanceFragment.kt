package com.example.iss.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iss.LocationUtils
import com.example.iss.R
import com.example.iss.viewmodel.LocationAndroidViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shows device location, ISS location and the distance between them.
 */
@AndroidEntryPoint
class ISSDistanceFragment : Fragment() {
    companion object {
        const val TAG = "ISSDistanceFragment"
    }

    private val locationAndroidViewModel: LocationAndroidViewModel by lazy {
        ViewModelProvider(this)[LocationAndroidViewModel::class.java]
    }

    private lateinit var deviceLatitude: TextView
    private lateinit var deviceLongitude: TextView

    private lateinit var issLatitude: TextView
    private lateinit var issLongitude: TextView
    private lateinit var viewISSLocation: Button

    private lateinit var issDistance: TextView
    private var issLocation: Location? = null

    private val viewISSLocationClickListener = OnClickListener { view ->
        issLocation?.let { location ->
            LocationUtils.showLocationOnGoogleMap(view.context, location)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.iss_distance_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceLatitude = view.findViewById(R.id.device_lat)
        deviceLongitude = view.findViewById(R.id.device_long)

        issLatitude = view.findViewById(R.id.iss_lat)
        issLongitude = view.findViewById(R.id.iss_long)

        viewISSLocation = view.findViewById(R.id.view_iss_location)
        viewISSLocation.setOnClickListener(viewISSLocationClickListener)

        issDistance = view.findViewById(R.id.distance)

        attachObservers()
    }

    override fun onStart() {
        super.onStart()
        if (LocationUtils.locationPermissionGranted(requireContext())) {
            locationAndroidViewModel.requestDeviceAndISSLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        if (LocationUtils.locationPermissionGranted(requireContext())) {
            locationAndroidViewModel.removeDeviceAndISSLocationUpdates()
        }
    }

    private fun attachObservers() {
        locationAndroidViewModel.deviceAndISSLocationLiveData.observe(viewLifecycleOwner) {
            val deviceLocation = it.deviceLocation
            val issLocation = LocationUtils.toLocation(it.issLocation)
            this.issLocation = issLocation

            updateLocationText(
                latitudeTextView = issLatitude,
                longitudeTextView = issLongitude,
                location = issLocation
            )

            updateLocationText(
                latitudeTextView = deviceLatitude,
                longitudeTextView = deviceLongitude,
                location = deviceLocation
            )

            val distanceInMiles = locationAndroidViewModel.calculateDistanceInMiles(
                source = deviceLocation,
                destination = issLocation
            )
            issDistance.text =
                getString(R.string.iss_distance_in_miles, String.format("%.2f", distanceInMiles))
        }
    }

    private fun updateLocationText(
        latitudeTextView: TextView,
        longitudeTextView: TextView,
        location: Location
    ) {
        latitudeTextView.text = getString(R.string.latitude, location.latitude)
        longitudeTextView.text = getString(R.string.longitude, location.longitude)
    }
}