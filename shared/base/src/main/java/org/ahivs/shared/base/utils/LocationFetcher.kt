package org.ahivs.shared.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.ahivs.shared.base.di.AppScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Use this class only when you have access to Android permission ACCESS_FINE_LOCATION,
 *  otherwise it won't return the needed location info
 */
@AppScope
class LocationFetcher @Inject constructor(applicationContext: Context) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    private val cancellationTokenSource = CancellationTokenSource()

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private suspend fun fetchLocation(): LocationInfo {
        return suspendCancellableCoroutine { cont ->
            val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )

            val completeListener = OnCompleteListener<Location> { task ->
                if (task.isSuccessful && task.result != null) {
                    val location: Location = task.result
                    cont.resume(LocationFetchSuccess(location.latitude, location.longitude)) {}
                    cancel()
                } else {
                    val exception = task.exception
                    cont.resume(
                        LocationFetchError(
                            exception?.message ?: "Error fetching location"
                        )
                    ) {}
                    cancel()
                }
            }
            currentLocationTask.addOnCompleteListener(completeListener)
            cont.invokeOnCancellation {
                cancel()
            }
        }
    }

    fun cancel() {
        cancellationTokenSource.cancel()
    }

    suspend fun fetchCurrentLocation(): LocationInfo {
        return try {
            fetchLocation()
        } catch (exception: Exception) {
            LocationFetchError(exception.message ?: "Error fetching location")
        }
    }

}

sealed class LocationInfo
class LocationFetchSuccess(val latitude: Double, val longitude: Double) : LocationInfo()
class LocationFetchError(val errorMsg: String) : LocationInfo()