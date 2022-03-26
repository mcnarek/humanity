package com.humanity.weatherapp.domain.repository

import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.domain.enums.WeatherUnits
import retrofit2.Response


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
interface PlaceRepository {
    suspend fun getPlace(): PlaceEntity?
}

interface WeatherRepository {
    suspend fun getWeather(
        lng: Double,
        lat: Double,
        weatherUnits: WeatherUnits? = null,
        language: String? = null
    ): Response<WeatherResponse>
}