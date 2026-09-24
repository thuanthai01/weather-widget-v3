package com.hyperos.weather.widget

object WeatherUtils {

    fun getDescription(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Trời quang"

            1, 2 -> "Có mây"
            3 -> "Nhiều mây"

            45, 48 -> "Sương mù"

            51, 53, 55 -> "Mưa phùn"

            56, 57 -> "Mưa phùn lạnh"

            61, 63, 65 -> "Mưa"
            66, 67 -> "Mưa lạnh"

            71, 73, 75, 77 -> "Tuyết"

            80, 81, 82 -> "Mưa rào"

            85, 86 -> "Mưa tuyết"

            95 -> "Dông"

            96, 99 -> "Dông có mưa đá"

            else -> "Không xác định"
        }
    }

    fun getIcon(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "☀️"

            1 -> "🌤️"
            2 -> "⛅"
            3 -> "☁️"

            45, 48 -> "🌫️"

            51, 53, 55,
            56, 57 -> "🌦️"

            61, 63, 65,
            66, 67 -> "🌧️"

            71, 73, 75, 77,
            85, 86 -> "🌨️"

            80, 81, 82 -> "🌦️"

            95, 96, 99 -> "⛈️"

            else -> "🌡️"
        }
    }
}
