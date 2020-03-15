package com.skinnydoo.coffeeloc8r.domain.models

import androidx.annotation.Keep

@Keep
data class CoffeeShopHours(
    val id: String,
    val day: Int,
    val opening: String?,
    val closing: String?,
    val open: Boolean
)
