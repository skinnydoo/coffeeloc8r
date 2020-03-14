package com.skinnydoo.coffeeloc8r.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VenueExploreResponse(val response: Response) {

    @Keep
    data class Response(val groups: List<Group>)

    @Keep
    data class Group(val items: List<Item>)

    @Keep
    data class Item(@SerializedName("venue") val coffeeShop: CoffeeShopDto)
}
