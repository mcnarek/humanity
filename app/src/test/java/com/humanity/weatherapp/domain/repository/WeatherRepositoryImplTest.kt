package com.humanity.weatherapp.domain.repository

import com.humanity.weatherapp.BuildConfig
import com.humanity.weatherapp.api.data.request.weather.WeatherQuery
import com.humanity.weatherapp.api.services.WeatherService
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

/**
 * Created by Narek Hayrapetyan on 27 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class WeatherRepositoryImplTest : TestCase() {
    private lateinit var _service: WeatherService

    @Before
    public override fun setUp() {
        super.setUp()

        Dispatchers.resetMain()

        _service = MockWeatherService()
    }

    @Test
    fun testGetWeatherSuccess() = runBlocking {
        val random = Random(1000)
        val queryParams = WeatherQuery(
            lat = random.nextDouble(),
            lon = random.nextDouble(),
            appId = BuildConfig.APPLICATION_ID
        )
        val result = _service.getWeather(queryParams.toMap())
        assertTrue(result.isSuccessful)
    }
}