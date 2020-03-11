package com.skinnydoo.coffeeloc8r.ui.details.models.viewtype

import android.view.ViewGroup
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.ui.details.models.ContactItem
import com.skinnydoo.coffeeloc8r.ui.details.models.MapItem
import com.skinnydoo.coffeeloc8r.ui.details.models.PoweredByItem

interface DetailsViewTypeFactory {
    fun type(contactItem: ContactItem): Int
    fun type(contactItem: PoweredByItem): Int
    fun type(mapItem: MapItem): Int


    fun holder(
        parent: ViewGroup,
        viewType: Int,
        appExecutors: AppExecutors
    ): BaseViewHolder<*>


}
