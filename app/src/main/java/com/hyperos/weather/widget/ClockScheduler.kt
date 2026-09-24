package com.hyperos.weather.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object ClockScheduler {

    private const val REQUEST_CODE = 22092026

    fun schedule(context: Context) {

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val intent =
            Intent(
                context,
                ClockUpdateReceiver::class.java
            )

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        // Đặt lần cập nhật tiếp theo đúng đầu phút
        val currentTime =
            System.currentTimeMillis()

        val nextMinute =
            ((currentTime / 60000L) + 1) * 60000L

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextMinute,
                pendingIntent
            )

        } else {

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                nextMinute,
                pendingIntent
            )
        }
    }
}
