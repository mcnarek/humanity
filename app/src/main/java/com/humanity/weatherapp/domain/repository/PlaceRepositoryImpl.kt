package com.humanity.weatherapp.domain.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.managers.places.PlaceManager
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val _placeManager: PlaceManager
) : PlaceRepository {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override suspend fun getPlace(): PlaceEntity? {
        return _placeManager.getPlaceInfo()
    }
}