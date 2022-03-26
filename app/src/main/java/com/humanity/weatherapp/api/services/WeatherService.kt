package com.humanity.weatherapp.api.services

import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
interface WeatherService {
    @GET("weather")
    suspend fun getWeather(@QueryMap queryParams: Map<String, String>): Response<WeatherResponse>
}