package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsPoweredByBinding
import com.skinnydoo.coffeeloc8r.ui.details.models.PoweredByItem

class PoweredByItemViewHolder(
    val binding: ListItemShopDetailsPoweredByBinding
) : BaseViewHolder<PoweredByItem>(binding.root) {
    override fun bind(item: PoweredByItem) {
        // no binding needed
    }
}
