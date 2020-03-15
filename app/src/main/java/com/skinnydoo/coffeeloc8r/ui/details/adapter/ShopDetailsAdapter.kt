package com.skinnydoo.coffeeloc8r.ui.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsActor
import com.skinnydoo.coffeeloc8r.ui.details.models.ShopDetailsItem
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory

class ShopDetailsAdapter(
    private val appExecutors: AppExecutors,
    private val typeFactory: DetailsViewTypeFactory,
    val actor: DetailsActor
) : ListAdapter<ShopDetailsItem, BaseViewHolder<ShopDetailsItem>>(
    AsyncDifferConfig.Builder(ShopDetailsItemDiff)
        .setBackgroundThreadExecutor(appExecutors.diskIO)
        .build()
) {
    private val viewPool = RecyclerView.RecycledViewPool() // viewPool for child recyclers

    init {
        setHasStableIds(true)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ShopDetailsItem> {
        return typeFactory.holder(
            parent,
            viewType,
            appExecutors,
            viewPool,
            actor
        ) as BaseViewHolder<ShopDetailsItem>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ShopDetailsItem>, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type(typeFactory)
}


private object ShopDetailsItemDiff : DiffUtil.ItemCallback<ShopDetailsItem>() {

    override fun areItemsTheSame(oldItem: ShopDetailsItem, newItem: ShopDetailsItem): Boolean {
        return oldItem.isTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: ShopDetailsItem, newItem: ShopDetailsItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}
