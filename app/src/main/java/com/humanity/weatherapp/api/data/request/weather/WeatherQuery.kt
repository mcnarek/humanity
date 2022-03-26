package com.humanity.weatherapp.api.data.request.weather

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


/**
 * Created by Narek Hayrapetyan on 25 Mar 2022.
 * Copyright: WeatherApp
 * E-Mail: mcnarek@gmail.com
 */
data class WeatherQuery(
    @SerializedName("lat")
    val lat: Double = 0.0,

    @SerializedName("lon")
    val lon: Double = 0.0,

    @SerializedName("appid")
    val appId: String,

    @Expose
    @SerializedName("units")
    val units: String? = null,

    @Expose
    @SerializedName("lang")
    val lang: String? = null,
) {
    fun toMap(): Map<String, String> = convert()

    //convert an object of type I to type O
    private inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}
