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

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {

            try {

                WeatherUpdateManager.updateWidget(
                    context.applicationContext
                )

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                pendingResult.finish()
            }
        }
    }
}
