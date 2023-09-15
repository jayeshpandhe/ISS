package com.example.iss.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import com.example.iss.Constants
import com.example.iss.LocationUtils
import com.example.iss.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Application's main UI
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private val fragmentResultListener = FragmentResultListener { requestKey, result ->
        if (requestKey == Constants.PERMISSION_STATUS &&
            result.getBoolean(Constants.PERMISSION_GRANTED, false)
        ) {
            onLocationPermissionGranted()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchAstronautsListFragment()
    }

    override fun onStart() {
        super.onStart()
        if (LocationUtils.locationPermissionGranted(this)) {
            onLocationPermissionGranted()
        } else {
            launchPermissionFragment()
        }
    }

    private fun onLocationPermissionGranted() {
        launchISSDistanceFragment()
    }

    private fun launchPermissionFragment() {
        val locationPermissionFragment = LocationPermissionFragment()
        supportFragmentManager.setFragmentResultListener(
            Constants.PERMISSION_STATUS,
            this,
            fragmentResultListener
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.small_container, locationPermissionFragment, LocationPermissionFragment.TAG)
            .commit()
    }

    private fun launchISSDistanceFragment() {
        val issDistance = ISSDistanceFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.small_container, issDistance, ISSDistanceFragment.TAG)
            .commit()
    }

    private fun launchAstronautsListFragment() {
        val astronautsListFragment = AstronautsListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.large_container, astronautsListFragment, AstronautsListFragment.TAG)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iss_location_history -> {
                val intent = Intent(this, ISSLocationHistoryActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
