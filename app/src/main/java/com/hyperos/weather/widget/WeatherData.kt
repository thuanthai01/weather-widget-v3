package com.hyperos.weather.widget

data class WeatherData(
    val currentTemperature: Double,
    val currentWeatherCode: Int,
    val locationName: String,
    val forecast: List<DailyForecast>
)

data class DailyForecast(
    val date: String,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherCode: Int
)
