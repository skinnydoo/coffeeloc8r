package com.skinnydoo.coffeeloc8r.ui.home.models

import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.utils.event.Event

data class HomeViewState(
    val showProgress: Boolean,
    val error: Event<Int>?,
    val success: Event<List<CoffeeShop>>?
)
