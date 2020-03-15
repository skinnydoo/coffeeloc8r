package com.skinnydoo.coffeeloc8r.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetVenueResponse(val response: Response) {

    @Keep
    data class Response(@SerializedName("venue") val coffeeShop: CoffeeShopDto)
}
