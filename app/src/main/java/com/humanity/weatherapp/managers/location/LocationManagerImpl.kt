package com.humanity.weatherapp.managers.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.humanity.weatherapp.R

class LocationManagerImpl constructor(
    private val _context: Context,
) : LocationManager {

    override var onLocationUpdatedCallBack: ((Location) -> Unit)? = null
    override var onStartLocation: ((Location) -> Unit)? = null
    override var onError: ((String) -> Unit)? = null

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(_context)
    }

    private val locationCallback: LocationCallback

    private var startLocation: Location? = null

    init {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Log.d("locationUpdate:", "---> ${location.latitude} : ${location.longitude}")

                    if (location == null) {
                        onError?.invoke(_context.getString(R.string.location_fetch_error))
                    } else {
                        onLocationUpdatedCallBack?.invoke(location)
                    }
                }
            }
        }
    }

    /**
     * start requesting location data
     */
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("currentLocation:::", "--->${location?.latitude} : ${location?.longitude}")

            location?.let {
                startLocation = it
                onStartLocation?.invoke(it)
            }?:run {
                onError?.invoke(_context.getString(R.string.location_fetch_error))
            }
        }
    }
}