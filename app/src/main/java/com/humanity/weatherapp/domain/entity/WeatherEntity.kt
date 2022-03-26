package com.humanity.weatherapp.domain.entity

import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import com.humanity.weatherapp.R
import com.humanity.weatherapp.domain.enums.WeatherUnits


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
data class WeatherEntity(
    val weatherId: Int? = null,
    val name: String? = null,
    val weatherTemperature: Int? = null,
    val minTemperature: Int? = null,
    val maxTemperature: Int? = null,
    val iconUrl: String? = null,
    val windSpeed: Double? = null,
    val windDegree: Int? = null,
    val sunRise: Int? = null,
    val sunset: Int? = null,
    val country: String? = null,
    val description: String? = null,
    val units: WeatherUnits = WeatherUnits.STANDARD,
) {
    @get:ColorRes
    val weatherColor: Int
        get() = when (weatherId) {
            in 200..299 -> R.color.thunderstorm_color
            in 300..399 -> R.color.drizzle_color
            in 500..599 -> R.color.rain_color
            in 600..699 -> R.color.snow_color
            in 700..799 -> R.color.fog_color
            800 -> R.color.clear_color
            in 801..804 -> R.color.clouds_color
            else -> R.color.none
        }

    @get:RawRes
    val animation: Int?
        get() = when (weatherId) {
            in 200..299 -> R.raw.thunderstorm
            in 300..399 -> R.raw.cloudy
            in 500..599 -> R.raw.rain
            in 600..699 -> R.raw.snow
            in 700..799 -> R.raw.cloudy
            800 -> R.raw.sunny
            in 801..804 -> R.raw.cloudy
            else -> null
        }

}
