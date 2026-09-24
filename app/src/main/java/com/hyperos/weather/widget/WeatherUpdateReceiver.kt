package com.hyperos.weather.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        val appContext =
            context.applicationContext

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {

            try {

                // Lấy vị trí thực tế
                // và cập nhật dữ liệu thời tiết
                WeatherUpdateManager.updateWidget(
                    appContext
                )

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                // Đặt lịch cho lần cập nhật tiếp theo
                WeatherScheduler.schedule(
                    appContext
                )

                pendingResult.finish()
            }
        }
    }
}
