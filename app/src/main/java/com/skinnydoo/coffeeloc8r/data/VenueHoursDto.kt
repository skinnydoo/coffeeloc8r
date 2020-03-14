package com.skinnydoo.coffeeloc8r.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
data class VenueHoursDto(
    val id: String = UUID.randomUUID().toString(),
    @SerializedName("timeframes")
    val timeFrames: List<TimeFrames>

)

@Keep
data class TimeFrames(
    val days: List<Int>,
    val open: List<Open>
)

@Keep
data class Open(
    val start: String,
    val end: String
)
