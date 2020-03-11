package com.skinnydoo.coffeeloc8r.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.databinding.ListItemBottomSheetBinding
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.common.DataBoundListAdapter
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeActor
import com.skinnydoo.coffeeloc8r.utils.extensions.bind

class BottomSheetAdapter(
    appExecutors: AppExecutors,
    private val actor: HomeActor
) : DataBoundListAdapter<CoffeeShop, ListItemBottomSheetBinding>(
    appExecutors,
    BottomSheetItemDiff
) {

    override fun createBinding(parent: ViewGroup): ListItemBottomSheetBinding {
        return parent.bind(R.layout.list_item_bottom_sheet)
    }

    override fun bind(binding: ListItemBottomSheetBinding, item: CoffeeShop, position: Int) {
        binding.item = item
        binding.actor = actor
    }
}

private object BottomSheetItemDiff : DiffUtil.ItemCallback<CoffeeShop>() {

    override fun areItemsTheSame(oldItem: CoffeeShop, newItem: CoffeeShop): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoffeeShop, newItem: CoffeeShop): Boolean {
        return oldItem == newItem
    }
}
