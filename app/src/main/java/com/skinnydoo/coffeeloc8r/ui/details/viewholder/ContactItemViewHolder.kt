package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsContactsBinding
import com.skinnydoo.coffeeloc8r.ui.details.models.ContactItem
import com.skinnydoo.coffeeloc8r.utils.extensions.executeAfter

class ContactItemViewHolder(
    private val binding: ListItemShopDetailsContactsBinding
) : BaseViewHolder<ContactItem>(binding.root) {
    override fun bind(item: ContactItem) {
        binding.executeAfter {
            this.item = item
        }
    }
}
