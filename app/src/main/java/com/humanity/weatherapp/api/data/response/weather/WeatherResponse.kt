package com.humanity.weatherapp.api.data.response.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
	@field:SerializedName("visibility")
	val visibility: Int = 0,

	@field:SerializedName("timezone")
	val timezone: Int = 0,

	@field:SerializedName("main")
	val main: Main? = null,

	@field:SerializedName("clouds")
	val clouds: Clouds? = null,

	@field:SerializedName("sys")
	val sys: Sys? = null,

	@field:SerializedName("dt")
	val dt: Int = 0,

	@field:SerializedName("coord")
	val coord: Coord? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("cod")
	val cod: Int = 0,

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("base")
	val base: String? = null,

	@field:SerializedName("wind")
	val wind: Wind? = null
)

data class Clouds(
	@field:SerializedName("all")
	val all: Int = 0
)

data class WeatherItem(
	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int = 0
)

data class Coord(
	@field:SerializedName("lon")
	val lon: Double = 0.0,

	@field:SerializedName("lat")
	val lat: Double = 0.0
)

data class Main(

	@field:SerializedName("temp")
	val temp: Double = 0.0,

	@field:SerializedName("temp_min")
	val tempMin: Double = 0.0,

	@field:SerializedName("humidity")
	val humidity: Int = 0,

	@field:SerializedName("pressure")
	val pressure: Int = 0,

	@field:SerializedName("feels_like")
	val feelsLike: Double = 0.0,

	@field:SerializedName("temp_max")
	val tempMax: Double = 0.0
)

data class Sys(
	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int = 0,

	@field:SerializedName("sunset")
	val sunset: Int = 0,

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("type")
	val type: Int = 0,

	@field:SerializedName("message")
	val message: Double = 0.0
)

data class Wind(
	@field:SerializedName("deg")
	val deg: Int = 0,

	@field:SerializedName("speed")
	val speed: Double = 0.0
)
