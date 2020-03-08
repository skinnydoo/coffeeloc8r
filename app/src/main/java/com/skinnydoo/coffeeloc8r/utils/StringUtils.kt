package com.skinnydoo.coffeeloc8r.utils

import com.skinnydoo.coffeeloc8r.utils.extensions.format

object StringUtils {

    @JvmStatic
    fun formattedDistance(distance: Double?): String = when {
        distance == null -> ""
        distance < 0.0 -> "0m"
        distance <= 0.999 -> "${(distance * 1000).toInt()} m"
        else -> "${distance.format(0)} km"
    }

}
