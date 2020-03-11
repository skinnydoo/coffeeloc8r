package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsDescriptionBinding
import com.skinnydoo.coffeeloc8r.ui.details.models.DescriptionItem
import com.skinnydoo.coffeeloc8r.utils.extensions.executeAfter

class DescriptionItemViewHolder(
    private val binding: ListItemShopDetailsDescriptionBinding
) : BaseViewHolder<DescriptionItem>(binding.root) {
    override fun bind(item: DescriptionItem) {
        binding.executeAfter {
            this.item = item
        }
    }
}
