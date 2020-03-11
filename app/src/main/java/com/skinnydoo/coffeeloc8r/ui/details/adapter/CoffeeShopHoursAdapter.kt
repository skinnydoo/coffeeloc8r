package com.skinnydoo.coffeeloc8r.ui.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.DataBoundListAdapter
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsCoffeeShopHoursBinding
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShopHours
import com.skinnydoo.coffeeloc8r.utils.extensions.bind

class CoffeeShopHoursAdapter(
    appExecutors: AppExecutors
) : DataBoundListAdapter<CoffeeShopHours, ListItemShopDetailsCoffeeShopHoursBinding>(
    appExecutors,
    CoffeeShopHoursDiff
) {

    override fun createBinding(parent: ViewGroup): ListItemShopDetailsCoffeeShopHoursBinding {
        return parent.bind(R.layout.list_item_shop_details_coffee_shop_hours)
    }

    override fun bind(
        binding: ListItemShopDetailsCoffeeShopHoursBinding,
        item: CoffeeShopHours,
        position: Int
    ) {
        binding.item = item
    }
}

private object CoffeeShopHoursDiff : DiffUtil.ItemCallback<CoffeeShopHours>() {
    override fun areItemsTheSame(oldItem: CoffeeShopHours, newItem: CoffeeShopHours): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoffeeShopHours, newItem: CoffeeShopHours): Boolean {
        return oldItem == newItem

    }
}
