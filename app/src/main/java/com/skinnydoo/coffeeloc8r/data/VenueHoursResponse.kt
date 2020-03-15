package com.skinnydoo.coffeeloc8r.data

import androidx.annotation.Keep

@Keep
data class VenueHoursResponse(val response: Response) {

    @Keep
    data class Response(val hours: VenueHoursDto)
}
