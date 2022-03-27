package com.humanity.weatherapp.domain.repository

import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.api.services.WeatherService
import org.mockito.Mockito
import retrofit2.Response


/**
 * Created by Narek Hayrapetyan on 27 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class MockWeatherService : WeatherService {
    override suspend fun getWeather(queryParams: Map<String, String>): Response<WeatherResponse> {
        return Response.success(Mockito.mock(WeatherResponse::class.java))
    }
}