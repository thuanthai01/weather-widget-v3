package com.hyperos.weather.widget

import android.content.Context
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

object LocationNameHelper {

    suspend fun getLocationName(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String {

        return try {

            if (!Geocoder.isPresent()) {
                return "Vị trí hiện tại"
            }

            val geocoder = Geocoder(
                context,
                Locale("vi", "VN")
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                suspendCancellableCoroutine { continuation ->

                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    ) { addresses ->

                        val address = addresses.firstOrNull()

                        val name =
                            address?.locality
                                ?: address?.subAdminArea
                                ?: address?.adminArea
                                ?: "Vị trí hiện tại"

                        continuation.resume(name)
                    }
                }

            } else {

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )

                val address = addresses?.firstOrNull()

                address?.locality
                    ?: address?.subAdminArea
                    ?: address?.adminArea
                    ?: "Vị trí hiện tại"
            }

        } catch (e: Exception) {

            e.printStackTrace()

            "Vị trí hiện tại"
        }
    }
}
