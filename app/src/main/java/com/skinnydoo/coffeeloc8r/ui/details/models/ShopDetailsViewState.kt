package com.skinnydoo.coffeeloc8r.ui.details.models

import com.skinnydoo.coffeeloc8r.utils.event.Event

data class ShopDetailsViewState(
    val showProgress: Boolean,
    val error: Event<Int>?,
    val items: List<ShopDetailsItem>?
)
