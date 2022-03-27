package com.humanity.weatherapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.humanity.weatherapp.domain.entity.ErrorData


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
abstract class BaseViewModel : ViewModel() {
    protected val errorLiveData: MutableLiveData<ErrorData?> by lazy { MutableLiveData() }
    val error: MutableLiveData<ErrorData?>
        get() = errorLiveData
}