package com.skinnydoo.coffeeloc8r.ui.details.models

import com.skinnydoo.coffeeloc8r.utils.event.Event

data class ShopDetailsViewState(
    val showProgress: Boolean,
    val error: Event<Int>?,
    val success: ShopDetailsViewStateResult?
)

data class ShopDetailsViewStateResult(val imageUrl: String?, val items: List<ShopDetailsItem>)
