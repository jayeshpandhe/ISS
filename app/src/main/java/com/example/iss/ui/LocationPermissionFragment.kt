package com.example.iss.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.iss.Constants
import com.example.iss.LocationUtils
import com.example.iss.R

/**
 * Manages location permission
 */
class LocationPermissionFragment : Fragment() {
    companion object {
        const val TAG = "LocationPermissionFragment"
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onLocationPermissionGranted()
                permissionStatusText.text = getString(R.string.location_permission_granted)
                requestLocationPermissionBtn.visibility = View.GONE
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionStatusText.text = getString(R.string.location_permission_msg)
                requestLocationPermissionBtn.visibility = View.VISIBLE
            } else {
                permissionStatusText.text = getString(R.string.location_permission_denied_msg)
                requestLocationPermissionBtn.visibility = View.GONE
            }
        }

    private fun onLocationPermissionGranted() {
        val result = Bundle()
        result.putBoolean(Constants.PERMISSION_GRANTED, true)
        parentFragmentManager.setFragmentResult(Constants.PERMISSION_STATUS, result)
    }

    private lateinit var permissionStatusText: TextView
    private lateinit var requestLocationPermissionBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.permission_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionStatusText = view.findViewById(R.id.permission_status_text)
        requestLocationPermissionBtn = view.findViewById(R.id.request_location_permission)
        requestLocationPermissionBtn.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onStart() {
        super.onStart()
        if (LocationUtils.locationPermissionGranted(requireContext())) {
            onLocationPermissionGranted()
            permissionStatusText.text = getString(R.string.location_permission_granted)
            requestLocationPermissionBtn.visibility = View.GONE
        } else {
            permissionStatusText.text = getString(R.string.location_permission_msg)
            requestLocationPermissionBtn.visibility = View.VISIBLE
        }
    }
}