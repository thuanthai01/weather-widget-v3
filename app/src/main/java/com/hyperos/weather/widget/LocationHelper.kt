package com.hyperos.weather.widget

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object LocationHelper {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        context: Context
    ): Location? {

        return try {
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context)

            suspendCancellableCoroutine { continuation ->

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->

                        if (location != null) {
                            continuation.resume(location)
                        } else {
                            requestFreshLocation(
                                fusedLocationClient,
                                continuation
                            )
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(null)
                    }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestFreshLocation(
        client: com.google.android.gms.location.FusedLocationProviderClient,
        continuation: kotlinx.coroutines.CancellableContinuation<Location?>
    ) {

        client.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            null
        )
            .addOnSuccessListener { location ->
                continuation.resume(location)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}
