package com.hyperos.weather.widget

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

object WeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherData? {

        return try {
            val urlString = String.format(
                Locale.US,
                "https://api.open-meteo.com/v1/forecast" +
                        "?latitude=%.6f" +
                        "&longitude=%.6f" +
                        "&current=temperature_2m,weather_code" +
                        "&daily=weather_code,temperature_2m_max,temperature_2m_min" +
                        "&forecast_days=4" +
                        "&timezone=auto",
                latitude,
                longitude
            )

            val connection =
                URL(urlString).openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.setRequestProperty("Accept", "application/json")

            try {
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return null
                }

                val response = connection.inputStream
                    .bufferedReader()
                    .use { it.readText() }

                val json = JSONObject(response)

                val current = json.getJSONObject("current")
                val daily = json.getJSONObject("daily")

                val currentTemperature =
                    current.getDouble("temperature_2m")

                val currentWeatherCode =
                    current.getInt("weather_code")

                val dates =
                    daily.getJSONArray("time")

                val weatherCodes =
                    daily.getJSONArray("weather_code")

                val maxTemperatures =
                    daily.getJSONArray("temperature_2m_max")

                val minTemperatures =
                    daily.getJSONArray("temperature_2m_min")

                val forecastList = mutableListOf<DailyForecast>()

                for (i in 0 until dates.length()) {
                    forecastList.add(
                        DailyForecast(
                            date = dates.getString(i),
                            minTemperature = minTemperatures.getDouble(i),
                            maxTemperature = maxTemperatures.getDouble(i),
                            weatherCode = weatherCodes.getInt(i)
                        )
                    )
                }

                WeatherData(
                    currentTemperature = currentTemperature,
                    currentWeatherCode = currentWeatherCode,
                    locationName = "",
                    forecast = forecastList
                )

            } finally {
                connection.disconnect()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
