package com.skinnydoo.coffeeloc8r.di

import android.content.Context
import com.google.android.gms.location.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocationModule {

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideSettingsClient(@ApplicationContext context: Context): SettingsClient {
        return LocationServices.getSettingsClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest =
        LocationRequest().apply {
            /**
             * Sets the desired interval for active location updates. This interval is inexact.
             * We may not receive updates at all if no location sources are available, or we
             * may receive them slower than requested. We may also receive update faster than
             * requested if other application are requesting location updates at a faster interval
             */
            // interval = UPDATE_INTERVAL_IN_MILLISECONDS

            /**
             * Sets the fastest rate for active location updates. This interval is exact, and the
             * application will never receive update faster than this value
             */
            // fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

    @Provides
    fun provideLocationSettingsRequest(locationRequest: LocationRequest): LocationSettingsRequest {
        return LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
    }


    companion object {
        // The desired interval for location updates.
        private const val UPDATE_INTERVAL_IN_MILLISECONDS = 10 * 60 * 1000L // 10 min
        // The fastest rate for active location updates.
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}
