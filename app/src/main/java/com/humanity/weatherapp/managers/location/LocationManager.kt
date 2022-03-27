package com.humanity.weatherapp.managers.location

import android.location.Location

/**
 * Location manager tp manage location updates, events, distances and positions
 */
interface LocationManager {
    var onStartLocation: ((Location) -> Unit)?

    /**
     * Listen to location updates
     */
    var onLocationUpdatedCallBack: ((Location) -> Unit)?

    var onError: ((String) -> Unit)?

    /**
     * start requesting location data
     */
    fun requestLocationUpdates()
}