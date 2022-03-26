package com.humanity.weatherapp.domain.usecase

import com.humanity.weatherapp.domain.entity.LocationData
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */

interface WeatherUseCase {
    suspend fun getCurrentWeather(locationData: LocationData): Flow<WeatherEntity>

    suspend fun getCurrentCityInfo(): Flow<PlaceEntity?>
}