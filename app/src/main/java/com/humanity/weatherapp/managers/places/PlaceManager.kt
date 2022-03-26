package com.humanity.weatherapp.managers.places

import com.humanity.weatherapp.domain.entity.PlaceEntity


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
interface PlaceManager {
    suspend fun getPlaceInfo(): PlaceEntity?
}