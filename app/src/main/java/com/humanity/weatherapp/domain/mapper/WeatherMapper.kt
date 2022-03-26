package com.humanity.weatherapp.domain.mapper

import com.humanity.weatherapp.api.data.response.weather.WeatherResponse
import com.humanity.weatherapp.domain.entity.WeatherEntity
import com.humanity.weatherapp.domain.enums.WeatherUnits


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
class WeatherMapper {
    fun mapWeather(from: WeatherResponse, units: WeatherUnits): WeatherEntity {
        return from.run {
            WeatherEntity(
                weatherId = weather?.firstOrNull()?.id,
                name = name,
                maxTemperature = main?.tempMax?.toInt(),
                minTemperature = main?.tempMin?.toInt(),
                iconUrl = "http://openweathermap.org/img/wn/${weather?.firstOrNull()?.icon}.png",
                weatherTemperature = main?.temp?.toInt(),
                windDegree = wind?.deg,
                windSpeed = wind?.speed,
                sunRise = sys?.sunrise,
                sunset = sys?.sunset,
                country = sys?.country,
                description = weather?.firstOrNull()?.description,
                units = units,
            )
        }
    }
}