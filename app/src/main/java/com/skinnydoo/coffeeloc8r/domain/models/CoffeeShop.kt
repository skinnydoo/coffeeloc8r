package com.skinnydoo.coffeeloc8r.domain.models

data class CoffeeShop(
    val id: String,
    val name: String,
    val rating: String?,
    val ratingColor: String?,
    val lat: Double,
    val lon: Double,
    val distance: Double,
    val open: Boolean
)
