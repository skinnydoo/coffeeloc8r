package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.ViewGroup
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsOpeningHoursBinding
import com.skinnydoo.coffeeloc8r.ui.details.adapter.CoffeeShopHoursAdapter
import com.skinnydoo.coffeeloc8r.ui.details.models.HoursItem
import com.skinnydoo.coffeeloc8r.utils.extensions.executeAfter
import com.skinnydoo.coffeeloc8r.utils.extensions.onClick

class HoursItemViewHolder(
    private val binding: ListItemShopDetailsOpeningHoursBinding,
    private val hoursAdapter: CoffeeShopHoursAdapter
) : BaseViewHolder<HoursItem>(binding.root) {
    override fun bind(item: HoursItem) {
        binding.item = item
        binding.isExpanded = false

        hoursAdapter.submitList(item.hours)

        binding.rightContainer.onClick {
            val parent = binding.openingHoursRecycler as? ViewGroup ?: return@onClick
            val expanded = binding.isExpanded ?: false

            val transition = TransitionInflater.from(binding.root.context)
                .inflateTransition(R.transition.shop_hours_toggle)
            TransitionManager.beginDelayedTransition(parent, transition)
            binding.executeAfter {
                isExpanded = !expanded
            }
        }
    }
}
