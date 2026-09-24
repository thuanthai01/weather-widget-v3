package com.hyperos.weather.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.defaultWeight
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HyperOSWeatherWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            WidgetContent(context)
        }
    }

    @Composable
    private fun WidgetContent(context: Context) {

        // =========================
        // THỜI GIAN THỰC TẾ
        // =========================

        val now = Date()

        val currentTime =
            SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(now)

        val currentDate =
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(now)

        val dayName =
            SimpleDateFormat(
                "EEEE",
                Locale("vi", "VN")
            ).format(now)

        // =========================
        // ÂM LỊCH
        // =========================

        val lunarDate =
            LunarCalendarUtils.today()

        // =========================
        // THỜI TIẾT ĐÃ LƯU
        // =========================

        val weather =
            WeatherCache.load(context)

        val currentTemperature =
            weather?.currentTemperature
                ?.let { "${it.toInt()}°C" }
                ?: "--°C"

        val weatherCode =
            weather?.currentWeatherCode ?: -1

        val weatherIcon =
            if (weatherCode >= 0) {
                WeatherUtils.getIcon(weatherCode)
            } else {
                "🌤️"
            }

        val weatherDescription =
            if (weatherCode >= 0) {
                WeatherUtils.getDescription(weatherCode)
            } else {
                "Đang cập nhật"
            }

        val locationName =
            weather?.locationName
                ?.takeIf { it.isNotBlank() }
                ?: "Vị trí hiện tại"

        // =========================
        // DỰ BÁO 3 NGÀY
        // =========================

        val forecast =
            weather?.forecast
                ?.drop(1)
                ?.take(3)
                ?: emptyList()

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(
                    ColorProvider(
                        Color(0x33FFFFFF)
                    )
                )
        ) {

            // =========================
            // HÀNG TRÊN
            // =========================

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Row(
                    modifier = GlanceModifier
                        .defaultWeight(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    // ĐỒNG HỒ
                    Text(
                        text = currentTime,
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontWeight =
                                FontWeight.Normal,
                            color = ColorProvider(
                                Color.White
                            )
                        )
                    )

                    Spacer(
                        modifier =
                            GlanceModifier.width(8.dp)
                    )

                    Column {

                        // THỨ + NGÀY
                        Text(
                            text =
                                "$dayName $currentDate",
                            style = TextStyle(
                                fontSize = 11.sp,
                                color =
                                    ColorProvider(
                                        Color.White
                                    )
                            )
                        )

                        // ÂM LỊCH
                        Text(
                            text =
                                "Âm lịch: ${lunarDate.day}/${lunarDate.month}",
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    ColorProvider(
                                        Color(0xFFFDE047)
                                    )
                            )
                        )

                        // CAN CHI
                        Text(
                            text =
                                "(${lunarDate.canChiYear})",
                            style = TextStyle(
                                fontSize = 10.sp,
                                color =
                                    ColorProvider(
                                        Color(0xFFFEF08A)
                                    )
                            )
                        )
                    }
                }

                // ĐƯỜNG KẺ DỌC
                Spacer(
                    modifier = GlanceModifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(
                            ColorProvider(
                                Color(0x33FFFFFF)
                            )
                        )
                )

                // =========================
                // THỜI TIẾT HIỆN TẠI
                // =========================

                Column(
                    horizontalAlignment =
                        Alignment.Start,
                    modifier =
                        GlanceModifier.padding(
                            start = 8.dp
                        )
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = weatherIcon,
                            style = TextStyle(
                                fontSize = 20.sp
                            )
                        )

                        Spacer(
                            modifier =
                                GlanceModifier.width(
                                    4.dp
                                )
                        )

                        Text(
                            text = currentTemperature,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight =
                                    FontWeight.Normal,
                                color =
                                    ColorProvider(
                                        Color.White
                                    )
                            )
                        )
                    }

                    Text(
                        text = "📍 $locationName",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                ColorProvider(
                                    Color.White
                                )
                        )
                    )

                    Text(
                        text = weatherDescription,
                        style = TextStyle(
                            fontSize = 10.sp,
                            color =
                                ColorProvider(
                                    Color(0xCCFFFFFF)
                                )
                        )
                    )
                }
            }

            // =========================
            // ĐƯỜNG KẺ NGANG
            // =========================

            Spacer(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        ColorProvider(
                            Color(0x33FFFFFF)
                        )
                    )
            )

            // =========================
            // DỰ BÁO 3 NGÀY
            // =========================

            Row(
                modifier =
                    GlanceModifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Spacer(
                    modifier =
                        GlanceModifier.defaultWeight()
                )

                for (item in forecast) {

                    ForecastColumn(
                        day = formatForecastDay(
                            item.date
                        ),
                        temp =
                            "${item.minTemperature.toInt()}° / " +
                            "${item.maxTemperature.toInt()}°",
                        icon =
                            WeatherUtils.getIcon(
                                item.weatherCode
                            ),
                        desc =
                            WeatherUtils.getDescription(
                                item.weatherCode
                            )
                    )

                    Spacer(
                        modifier =
                            GlanceModifier.defaultWeight()
                    )
                }

                // Nếu API chưa có dữ liệu,
                // vẫn giữ bố cục 3 cột.
                repeat(
                    3 - forecast.size
                ) {

                    ForecastColumn(
                        day = "--",
                        temp = "--° / --°",
                        icon = "🌤️",
                        desc = "Đang cập nhật"
                    )

                    Spacer(
                        modifier =
                            GlanceModifier.defaultWeight()
                    )
                }
            }
        }
    }

    @Composable
    private fun ForecastColumn(
        day: String,
        temp: String,
        icon: String,
        desc: String
    ) {

        Column(
            horizontalAlignment =
                Alignment.Start
        ) {

            Text(
                text = day,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        ColorProvider(
                            Color.White
                        )
                )
            )

            Text(
                text = icon,
                style = TextStyle(
                    fontSize = 14.sp
                )
            )

            Text(
                text = temp,
                style = TextStyle(
                    fontSize = 10.sp,
                    color =
                        ColorProvider(
                            Color.White
                        )
                )
            )

            Text(
                text = desc,
                style = TextStyle(
                    fontSize = 9.sp,
                    color =
                        ColorProvider(
                            Color(0xAAFFFFFF)
                        )
                )
            )
        }
    }

    private fun formatForecastDay(
        dateString: String
    ): String {

        return try {

            val inputFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
                )

            val outputFormat =
                SimpleDateFormat(
                    "EEE",
                    Locale("vi", "VN")
                )

            val date =
                inputFormat.parse(dateString)

            if (date != null) {
                outputFormat.format(date)
            } else {
                "--"
            }

        } catch (e: Exception) {
            "--"
        }
    }
}
