package com.humanity.weatherapp.ui

import com.humanity.weatherapp.domain.entity.LocationData
import com.humanity.weatherapp.domain.enums.WeatherUnits
import com.humanity.weatherapp.domain.usecase.WeatherUseCase
import com.humanity.weatherapp.managers.location.LocationManager
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Narek Hayrapetyan on 27 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class WeatherViewModelTest {
    private lateinit var locationManager: LocationManager
    private lateinit var weatherUseCase: WeatherUseCase
    lateinit var anyUnits: WeatherUnits

    @Before
    fun setUp() {
        locationManager = Mockito.mock(LocationManager::class.java)
        weatherUseCase = Mockito.mock(WeatherUseCase::class.java)
        anyUnits = Mockito.mock(WeatherUnits::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun changeUnits() {
        locationManager.requestLocationUpdates()
        runBlocking {
            val currentWeather = weatherUseCase.getCurrentWeather(LocationData(0.0, 0.0, anyUnits))
            Assert.assertNotNull(currentWeather)
        }
    }

    @Test
    fun startLocationUpdates() {
        locationManager.requestLocationUpdates()
    }
}