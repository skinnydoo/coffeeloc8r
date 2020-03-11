package com.skinnydoo.coffeeloc8r.ui.details.models.viewtype

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppConstants
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.*
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.details.adapter.CoffeeShopHoursAdapter
import com.skinnydoo.coffeeloc8r.ui.details.models.*
import com.skinnydoo.coffeeloc8r.ui.details.viewholder.*
import com.skinnydoo.coffeeloc8r.utils.extensions.bind
import com.skinnydoo.coffeeloc8r.utils.extensions.getLayoutInflater
import javax.inject.Inject

@PerActivity
class DetailsViewTypeFactoryImpl @Inject constructor() : DetailsViewTypeFactory {
    override fun type(contactItem: ContactItem): Int = R.layout.list_item_shop_details_contacts
    override fun type(contactItem: PoweredByItem): Int = R.layout.list_item_shop_details_powered_by
    override fun type(mapItem: MapItem): Int = R.layout.list_item_shop_details_map
    override fun type(descriptionItem: DescriptionItem): Int =
        R.layout.list_item_shop_details_description

    override fun type(hoursItem: HoursItem): Int = R.layout.list_item_shop_details_opening_hours

    override fun holder(
        parent: ViewGroup,
        viewType: Int,
        appExecutors: AppExecutors,
        viewPool: RecyclerView.RecycledViewPool
    ): BaseViewHolder<*> {
        return when (viewType) {
            R.layout.list_item_shop_details_contacts -> {
                val binding = parent.bind<ListItemShopDetailsContactsBinding>(viewType)
                ContactItemViewHolder(binding)
            }
            R.layout.list_item_shop_details_powered_by -> {
                val binding = ListItemShopDetailsPoweredByBinding.inflate(
                    parent.getLayoutInflater(),
                    parent,
                    false
                )
                PoweredByItemViewHolder(binding)

            }
            R.layout.list_item_shop_details_map -> {
                val binding = parent.bind<ListItemShopDetailsMapBinding>(viewType)
                MapItemViewHolder(binding)
            }
            R.layout.list_item_shop_details_description -> {
                val binding = parent.bind<ListItemShopDetailsDescriptionBinding>(viewType)
                DescriptionItemViewHolder(binding)
            }
            R.layout.list_item_shop_details_opening_hours -> {
                val hoursAdapter = CoffeeShopHoursAdapter(appExecutors)
                val binding = parent.bind<ListItemShopDetailsOpeningHoursBinding>(viewType).apply {
                    openingHoursRecycler.apply {
                        setRecycledViewPool(viewPool)
                        layoutManager = LinearLayoutManager(context).apply {
                            recycleChildrenOnDetach = true
                        }
                        adapter = hoursAdapter
                    }
                }
                HoursItemViewHolder(binding, hoursAdapter)
            }
            else -> throw IllegalStateException(AppConstants.VIEW_TYPE_ERROR)
        }
    }
}
