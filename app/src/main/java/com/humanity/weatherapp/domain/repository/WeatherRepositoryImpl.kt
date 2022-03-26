package com.humanity.weatherapp.domain.repository

import com.humanity.weatherapp.BuildConfig
import com.humanity.weatherapp.api.services.WeatherService
import com.humanity.weatherapp.api.data.request.weather.WeatherQuery
import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.domain.enums.WeatherUnits
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class WeatherRepositoryImpl @Inject constructor(private val _service: WeatherService) :
    WeatherRepository {
    override suspend fun getWeather(
        lng: Double,
        lat: Double,
        weatherUnits: WeatherUnits?,
        language: String?
    ): Response<WeatherResponse> {
        return _service.getWeather(
            WeatherQuery(
                lat = lat,
                lon = lng,
                appId = BuildConfig.API_KEY,
                lang = language,
                units = weatherUnits?.name?.lowercase(),
            ).toMap()
        )
    }
}