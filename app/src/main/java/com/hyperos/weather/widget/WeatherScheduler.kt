package com.hyperos.weather.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object WeatherScheduler {

    private const val REQUEST_CODE = 20260924

    private const val INTERVAL_MILLIS =
        30 * 60 * 1000L

    fun schedule(context: Context) {

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val intent =
            Intent(
                context,
                WeatherUpdateReceiver::class.java
            )

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val triggerTime =
            System.currentTimeMillis() +
                    INTERVAL_MILLIS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )

        } else {

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}
