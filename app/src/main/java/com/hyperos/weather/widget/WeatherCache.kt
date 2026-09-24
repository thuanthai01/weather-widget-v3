package com.hyperos.weather.widget

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object WeatherCache {

    private const val PREF_NAME = "weather_cache"

    private const val KEY_TEMPERATURE = "temperature"
    private const val KEY_WEATHER_CODE = "weather_code"
    private const val KEY_LOCATION = "location"
    private const val KEY_FORECAST = "forecast"

    fun save(
        context: Context,
        weather: WeatherData
    ) {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val forecastArray = JSONArray()

        weather.forecast.forEach { forecast ->

            val item = JSONObject()

            item.put("date", forecast.date)
            item.put(
                "min_temperature",
                forecast.minTemperature
            )
            item.put(
                "max_temperature",
                forecast.maxTemperature
            )
            item.put(
                "weather_code",
                forecast.weatherCode
            )

            forecastArray.put(item)
        }

        preferences.edit()
            .putFloat(
                KEY_TEMPERATURE,
                weather.currentTemperature.toFloat()
            )
            .putInt(
                KEY_WEATHER_CODE,
                weather.currentWeatherCode
            )
            .putString(
                KEY_LOCATION,
                weather.locationName
            )
            .putString(
                KEY_FORECAST,
                forecastArray.toString()
            )
            .apply()
    }

    fun load(
        context: Context
    ): WeatherData? {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        if (!preferences.contains(KEY_TEMPERATURE)) {
            return null
        }

        val temperature =
            preferences.getFloat(
                KEY_TEMPERATURE,
                0f
            ).toDouble()

        val weatherCode =
            preferences.getInt(
                KEY_WEATHER_CODE,
                0
            )

        val location =
            preferences.getString(
                KEY_LOCATION,
                "Vị trí hiện tại"
            )
                ?: "Vị trí hiện tại"

        val forecastString =
            preferences.getString(
                KEY_FORECAST,
                null
            )
                ?: return null

        val forecastArray =
            JSONArray(forecastString)

        val forecastList =
            mutableListOf<DailyForecast>()

        for (i in 0 until forecastArray.length()) {

            val item =
                forecastArray.getJSONObject(i)

            forecastList.add(
                DailyForecast(
                    date = item.getString("date"),
                    minTemperature =
                        item.getDouble("min_temperature"),
                    maxTemperature =
                        item.getDouble("max_temperature"),
                    weatherCode =
                        item.getInt("weather_code")
                )
            )
        }

        return WeatherData(
            currentTemperature = temperature,
            currentWeatherCode = weatherCode,
            locationName = location,
            forecast = forecastList
        )
    }
}
