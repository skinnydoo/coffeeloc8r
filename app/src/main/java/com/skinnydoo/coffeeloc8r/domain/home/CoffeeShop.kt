package com.skinnydoo.coffeeloc8r.domain.home

data class CoffeeShop(
    val id: String,
    val name: String,
    val open: Boolean,
    val distance: Double,
    val isFavorite: Boolean = false
)