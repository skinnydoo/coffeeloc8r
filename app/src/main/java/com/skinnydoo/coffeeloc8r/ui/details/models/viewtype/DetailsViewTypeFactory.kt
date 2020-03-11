package com.skinnydoo.coffeeloc8r.ui.details.models.viewtype

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.ui.details.models.*

interface DetailsViewTypeFactory {
    fun type(contactItem: ContactItem): Int
    fun type(contactItem: PoweredByItem): Int
    fun type(mapItem: MapItem): Int
    fun type(descriptionItem: DescriptionItem): Int
    fun type(hoursItem: HoursItem): Int


    fun holder(
        parent: ViewGroup,
        viewType: Int,
        appExecutors: AppExecutors,
        viewPool: RecyclerView.RecycledViewPool
    ): BaseViewHolder<*>
}
