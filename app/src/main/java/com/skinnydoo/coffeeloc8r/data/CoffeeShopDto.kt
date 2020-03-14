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
    val hours: Hours?,
    @SerializedName("canonicalUrl")
    val foursquareUrl: String?,
    val url: String?,
    val description: String?,
    val bestPhoto: Photo
)

@Keep
data class Contact(
    val formattedPhone: String?,
    val twitter: String?,
    val instagram: String?,
    val facebookUsername: String?
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

@Keep
data class Photo(
    val id: String,
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int
)

fun CoffeeShopDto.toCoffeeShop(): CoffeeShop = CoffeeShop(
    id,
    name = name,
    rating = rating?.toString(),
    ratingColor = ratingColor,
    lat = location.lat ?: 0.0,
    lon = location.lon ?: 0.0,
    distance = location.distance?.times(0.001) ?: 0.0,
    open = hours?.isOpen ?: false
)
