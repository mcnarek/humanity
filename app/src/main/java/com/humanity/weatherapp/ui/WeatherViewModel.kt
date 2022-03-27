package com.humanity.weatherapp.ui

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.humanity.weatherapp.base.BaseViewModel
import com.humanity.weatherapp.domain.entity.ErrorData
import com.humanity.weatherapp.domain.entity.LocationData
import com.humanity.weatherapp.domain.entity.PlaceEntity
import com.humanity.weatherapp.domain.entity.WeatherEntity
import com.humanity.weatherapp.domain.enums.WeatherUnits
import com.humanity.weatherapp.domain.usecase.WeatherUseCase
import com.humanity.weatherapp.managers.location.LocationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val _weatherUseCase: WeatherUseCase,
    private val _locationManager: LocationManager
) : BaseViewModel() {

    private val _weatherLiveData: MutableLiveData<WeatherEntity> by lazy { MutableLiveData() }
    val weather: MutableLiveData<WeatherEntity>
        get() = _weatherLiveData

    private val _placeLiveData: MutableLiveData<PlaceEntity> by lazy { MutableLiveData() }
    val place: MutableLiveData<PlaceEntity>
        get() = _placeLiveData

    private var currentLocation: Location? = null
    private var currentUnits: WeatherUnits = WeatherUnits.METRIC

    fun refresh() {
        _locationManager.requestLocationUpdates()

        currentLocation?.let { getWeather(it, currentUnits) }
    }

    fun changeUnits(units: WeatherUnits) {
        currentLocation?.let { getWeather(it, units) }
    }

    fun startLocationUpdates() {
        _locationManager.onLocationUpdatedCallBack = {
            getWeather(it, currentUnits)
        }

        _locationManager.onStartLocation = {
            getWeather(it, currentUnits)
        }

        _locationManager.onError = {
            errorLiveData.postValue(ErrorData(it))
        }

        _locationManager.requestLocationUpdates()
    }

    private fun getWeather(location: Location, units: WeatherUnits = WeatherUnits.METRIC) {
        currentUnits = units
        currentLocation = location

        val locationData = LocationData(
            latitude = location.latitude,
            longitude = location.longitude,
            units = units,
        )

        viewModelScope.launch {
            _weatherUseCase.getCurrentWeather(
                locationData = locationData
            ).catch {
                errorLiveData.postValue(ErrorData(it.message))
            }.collectLatest {
                _weatherLiveData.postValue(it)
            }

            _weatherUseCase.getCurrentCityInfo()
                .catch {
                    errorLiveData.postValue(ErrorData(it.message))
                }
                .collectLatest {
                    _placeLiveData.postValue(it)
                }
        }
    }
}