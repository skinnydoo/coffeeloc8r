package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsPoweredByBinding
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsActor
import com.skinnydoo.coffeeloc8r.ui.details.models.PoweredByItem

class PoweredByItemViewHolder(
    private val binding: ListItemShopDetailsPoweredByBinding,
    private val actor: DetailsActor
) : BaseViewHolder<PoweredByItem>(binding.root) {
    override fun bind(item: PoweredByItem) {
        binding.actor = actor
    }
}
