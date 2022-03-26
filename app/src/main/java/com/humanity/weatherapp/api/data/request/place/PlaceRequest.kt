package com.humanity.weatherapp.api.data.request.place


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
data class PlaceRequest(
    val lat: Double = 0.0,

    val lng: Double = 0.0,

    val placeId: String? = null
)
