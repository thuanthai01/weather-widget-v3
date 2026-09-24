package com.hyperos.weather.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class HyperOSWeatherWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = HyperOSWeatherWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        // Bắt đầu cập nhật đồng hồ mỗi phút
        ClockScheduler.schedule(context)

        // Bắt đầu cập nhật thời tiết
        WeatherScheduler.schedule(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)

        // Dừng lịch khi không còn widget
        ClockScheduler.cancel(context)
        WeatherScheduler.cancel(context)
    }
}
