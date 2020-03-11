package com.skinnydoo.coffeeloc8r.ui.details.models.viewtype

import android.view.ViewGroup
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppConstants
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsContactsBinding
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsMapBinding
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsPoweredByBinding
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.details.models.ContactItem
import com.skinnydoo.coffeeloc8r.ui.details.models.MapItem
import com.skinnydoo.coffeeloc8r.ui.details.models.PoweredByItem
import com.skinnydoo.coffeeloc8r.ui.details.viewholder.ContactItemViewHolder
import com.skinnydoo.coffeeloc8r.ui.details.viewholder.MapItemViewHolder
import com.skinnydoo.coffeeloc8r.ui.details.viewholder.PoweredByItemViewHolder
import com.skinnydoo.coffeeloc8r.utils.extensions.bind
import com.skinnydoo.coffeeloc8r.utils.extensions.getLayoutInflater
import javax.inject.Inject

@PerActivity
class DetailsViewTypeFactoryImpl @Inject constructor() : DetailsViewTypeFactory {
    override fun type(contactItem: ContactItem): Int = R.layout.list_item_shop_details_contacts
    override fun type(contactItem: PoweredByItem): Int = R.layout.list_item_shop_details_powered_by
    override fun type(mapItem: MapItem): Int = R.layout.list_item_shop_details_map

    override fun holder(
        parent: ViewGroup,
        viewType: Int,
        appExecutors: AppExecutors
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
            else -> throw IllegalStateException(AppConstants.VIEW_TYPE_ERROR)
        }
    }
}
