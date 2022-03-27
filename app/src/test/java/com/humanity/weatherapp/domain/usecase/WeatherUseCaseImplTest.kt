package com.humanity.weatherapp.domain.usecase

import com.humanity.weatherapp.BuildConfig
import com.humanity.weatherapp.api.data.request.weather.WeatherQuery
import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.api.services.WeatherService
import com.humanity.weatherapp.domain.enums.WeatherUnits
import com.humanity.weatherapp.domain.repository.MockWeatherService
import com.humanity.weatherapp.domain.repository.PlaceRepository
import com.humanity.weatherapp.domain.repository.WeatherRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import kotlin.random.Random

/**
 * Created by Narek Hayrapetyan on 27 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class WeatherUseCaseImplTest : TestCase() {
    private lateinit var _weatherRepository: WeatherRepository
    private lateinit var _placeRepository: PlaceRepository
    private lateinit var _weatherService: WeatherService

    public override fun setUp() {
        super.setUp()

        _weatherService = MockWeatherService()

        _weatherRepository = object : WeatherRepository {
            override suspend fun getWeather(
                lng: Double,
                lat: Double,
                weatherUnits: WeatherUnits?,
                language: String?
            ): Response<WeatherResponse> {
                return _weatherService.getWeather(
                    WeatherQuery(
                        lng,
                        lat,
                        BuildConfig.API_KEY
                    ).toMap()
                )
            }
        }
    }

    public override fun tearDown() {}

    fun testGetCurrentWeather() {
        runBlocking {
            val random = Random(1000)
            val weather: Response<WeatherResponse> = _weatherRepository.getWeather(random.nextDouble(), random.nextDouble())

            assert(weather.isSuccessful)
            assertNotNull("the body is null", weather.body())
        }
    }

    fun testGetCurrentCityInfo() {

    }
}