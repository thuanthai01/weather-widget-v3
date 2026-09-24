package com.hyperos.weather.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClockUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        val appContext =
            context.applicationContext

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.Default).launch {

            try {

                // Cập nhật nội dung widget
                HyperOSWeatherWidget()
                    .updateAll(appContext)

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                // Đặt lịch cho phút tiếp theo
                ClockScheduler.schedule(
                    appContext
                )

                pendingResult.finish()
            }
        }
    }
}
