package com.hyperos.weather.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        if (intent?.action ==
            Intent.ACTION_BOOT_COMPLETED
        ) {

            val appContext =
                context.applicationContext

            // Khởi động lại lịch cập nhật đồng hồ
            ClockScheduler.schedule(
                appContext
            )

            // Khởi động lại lịch cập nhật thời tiết
            WeatherScheduler.schedule(
                appContext
            )
        }
    }
}
