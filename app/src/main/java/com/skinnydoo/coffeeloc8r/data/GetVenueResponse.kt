package com.skinnydoo.coffeeloc8r.data

import com.google.gson.annotations.SerializedName

data class GetVenueResponse(val response: Response) {
    data class Response(@SerializedName("venue") val coffeeShop: CoffeeShopDto)
}
