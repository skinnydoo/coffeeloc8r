package com.skinnydoo.coffeeloc8r.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop

@Keep
data class CoffeeShopDto(
    val id: String,
    val name: String,
    val rating: Double?,
    val ratingColor: String?,
    val location: Location,
    val hours: Hours?
)


@Keep
data class Location(
    val address: String?,
    val crossStreet: String,
    val lat: Double?,
    @SerializedName("lng")
    val lon: Double?,
    val distance: Int? = 0,
    val postalCode: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val formattedAddress: List<String>?
)

@Keep
data class Hours(
    val status: String,
    val isOpen: Boolean
)

fun CoffeeShopDto.toCoffeeShop(distance: Double): CoffeeShop = CoffeeShop(
    id,
    name = name,
    rating = rating?.toString(),
    ratingColor = ratingColor,
    distance = distance.times(0.001),
    open = hours?.isOpen ?: false
)
