package com.humanity.weatherapp.domain.entity

import android.graphics.Bitmap


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
data class PlaceEntity(
    val cityName: String? = null,

    val placePhoto: Bitmap? = null
)