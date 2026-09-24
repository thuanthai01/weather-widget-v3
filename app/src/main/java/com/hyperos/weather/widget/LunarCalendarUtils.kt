package com.hyperos.weather.widget

import java.util.Calendar
import kotlin.math.floor

data class LunarDate(
    val day: Int,
    val month: Int,
    val year: Int,
    val canChiYear: String
)

object LunarCalendarUtils {

    private const val PI = Math.PI

    fun solarToLunar(
        day: Int,
        month: Int,
        year: Int,
        timeZone: Double = 7.0
    ): LunarDate {

        val jd = solarToJulianDay(
            day,
            month,
            year
        )

        val lunarYear = findLunarYear(jd, timeZone)

        val firstDayOfLunarYear =
            getLunarNewYearDay(lunarYear, timeZone)

        val lunarDay =
            (jd - firstDayOfLunarYear).toInt() + 1

        var lunarMonth = 1
        var lunarYearStart = firstDayOfLunarYear

        while (lunarMonth <= 13) {

            val monthStart =
                getLunarMonthStart(
                    lunarYear,
                    lunarMonth,
                    timeZone
                )

            val nextMonthStart =
                getLunarMonthStart(
                    lunarYear,
                    lunarMonth + 1,
                    timeZone
                )

            if (jd >= monthStart && jd < nextMonthStart) {

                val dayInMonth =
                    (jd - monthStart).toInt() + 1

                return LunarDate(
                    day = dayInMonth,
                    month = lunarMonth,
                    year = lunarYear,
                    canChiYear = getCanChiYear(lunarYear)
                )
            }

            lunarMonth++
        }

        return LunarDate(
            day = lunarDay,
            month = 1,
            year = lunarYear,
            canChiYear = getCanChiYear(lunarYear)
        )
    }

    fun today(): LunarDate {

        val calendar = Calendar.getInstance()

        return solarToLunar(
            day = calendar.get(Calendar.DAY_OF_MONTH),
            month = calendar.get(Calendar.MONTH) + 1,
            year = calendar.get(Calendar.YEAR)
        )
    }

    private fun solarToJulianDay(
        day: Int,
        month: Int,
        year: Int
    ): Double {

        var y = year
        var m = month

        if (m <= 2) {
            y -= 1
            m += 12
        }

        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)

        return floor(365.25 * (y + 4716)) +
                floor(30.6001 * (m + 1)) +
                day +
                b -
                1524.5
    }

    private fun findLunarYear(
        jd: Double,
        timeZone: Double
    ): Int {

        var year = Calendar.getInstance()
            .get(Calendar.YEAR)

        while (
            getLunarNewYearDay(
                year,
                timeZone
            ) > jd
        ) {
            year--
        }

        while (
            getLunarNewYearDay(
                year + 1,
                timeZone
            ) <= jd
        ) {
            year++
        }

        return year
    }

    private fun getLunarNewYearDay(
        year: Int,
        timeZone: Double
    ): Double {

        var day = 1

        while (day <= 31) {

            val jd = solarToJulianDay(
                day,
                1,
                year
            )

            if (getLunarMonthStart(
                    year,
                    1,
                    timeZone
                ) == jd
            ) {
                return jd
            }

            day++
        }

        return solarToJulianDay(
            1,
            1,
            year
        )
    }

    private fun getLunarMonthStart(
        year: Int,
        lunarMonth: Int,
        timeZone: Double
    ): Double {

        val approximateSolarMonth =
            lunarMonth * 29.53

        val newYear =
            getApproximateNewYear(
                year
            )

        return floor(
            newYear +
                    approximateSolarMonth -
                    29.53
        )
    }

    private fun getApproximateNewYear(
        year: Int
    ): Double {

        val lunarYear =
            year - 2000

        return solarToJulianDay(
            25,
            1,
            year
        ) + lunarYear * 354.367
    }

    private fun getCanChiYear(
        year: Int
    ): String {

        val can = arrayOf(
            "Giáp",
            "Ất",
            "Bính",
            "Đinh",
            "Mậu",
            "Kỷ",
            "Canh",
            "Tân",
            "Nhâm",
            "Quý"
        )

        val chi = arrayOf(
            "Tý",
            "Sửu",
            "Dần",
            "Mão",
            "Thìn",
            "Tỵ",
            "Ngọ",
            "Mùi",
            "Thân",
            "Dậu",
            "Tuất",
            "Hợi"
        )

        val canIndex =
            (year + 6) % 10

        val chiIndex =
            (year + 8) % 12

        return "${can[canIndex]} ${chi[chiIndex]}"
    }
}
