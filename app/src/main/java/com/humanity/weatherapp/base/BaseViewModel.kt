package com.humanity.weatherapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
abstract class BaseViewModel : ViewModel() {
    protected val errorLiveData: MutableLiveData<String?> by lazy { MutableLiveData() }
    val error: MutableLiveData<String?>
        get() = errorLiveData
}