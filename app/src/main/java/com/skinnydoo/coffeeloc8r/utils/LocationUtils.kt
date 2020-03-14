package com.skinnydoo.coffeeloc8r.utils

import android.location.Location

object LocationUtils {

    @JvmStatic
    fun distanceBetween(
        startLat: Double,
        startLon: Double,
        endLat: Double,
        endLon: Double
    ): Double {
        val results = FloatArray(3)
        Location.distanceBetween(startLat, startLon, endLat, endLon, results)
        return results[0].toDouble()
    }
}
