package com.humanity.weatherapp.domain.usecase

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.domain.entity.LocationData
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.domain.entity.WeatherEntity
import com.humanity.weatherapp.domain.enums.WeatherUnits
import com.humanity.weatherapp.domain.mapper.WeatherMapper
import com.humanity.weatherapp.domain.repository.PlaceRepository
import com.humanity.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCaseImpl @Inject constructor(
    private val _weatherRepository: WeatherRepository,
    private val _placeRepository: PlaceRepository
) : WeatherUseCase {
    override suspend fun getCurrentWeather(locationData: LocationData): Flow<WeatherEntity> {
        val response = _weatherRepository.getWeather(
            lat = locationData.latitude,
            lng = locationData.longitude,
            weatherUnits = locationData.units,
        )

        return if (response.isSuccessful) {
            val body: WeatherResponse? = response.body()
            if (body != null) {
                val mapWeather =
                    WeatherMapper().mapWeather(body, locationData.units ?: WeatherUnits.STANDARD)
                flow {
                    emit(mapWeather)
                }
            } else {
                flow {
                    throw Exception("null body")
                }
            }
        } else {
            flow {
                throw Exception(response.message())
            }
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override suspend fun getCurrentCityInfo(): Flow<PlaceEntity?> {
        val cityInfo = _placeRepository.getPlace()

        return flow { emit(cityInfo) }
    }
}