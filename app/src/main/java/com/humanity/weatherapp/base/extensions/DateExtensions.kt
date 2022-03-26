package com.humanity.weatherapp.base.extensions

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Narek Hayrapetyan on 26 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */

fun now(): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
}