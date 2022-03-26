package com.humanity.weatherapp.domain.entity

import com.humanity.weatherapp.domain.enums.WeatherUnits


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
data class LocationData(
    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val units: WeatherUnits? = null
)
