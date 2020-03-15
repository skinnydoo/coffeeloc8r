package com.skinnydoo.coffeeloc8r.ui.details.models

import com.skinnydoo.coffeeloc8r.common.Differentiable
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShopHours
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory

sealed class ShopDetailsItem : Differentiable {
    abstract fun type(typeFactory: DetailsViewTypeFactory): Int
}

data class ContactItem(
    val id: String,
    val text: String?,
    val type: ContactType
) : ShopDetailsItem() {
    override fun isTheSame(other: Differentiable): Boolean = (id == (other as? ContactItem)?.id)
    override fun type(typeFactory: DetailsViewTypeFactory): Int = typeFactory.type(this)
}

object PoweredByItem: ShopDetailsItem() {
    override fun type(typeFactory: DetailsViewTypeFactory): Int = typeFactory.type(this)
}

data class MapItem(
    val id: String,
    val lat: Double,
    val lon : Double,
    val address: String?,
    val addressDetails: String?
): ShopDetailsItem() {
    override fun isTheSame(other: Differentiable): Boolean = (id == (other as? MapItem)?.id)
    override fun type(typeFactory: DetailsViewTypeFactory): Int = typeFactory.type(this)
}

data class DescriptionItem(
    val id: String,
    val description: String?
) : ShopDetailsItem() {
    override fun isTheSame(other: Differentiable): Boolean = (id == (other as? DescriptionItem)?.id)
    override fun type(typeFactory: DetailsViewTypeFactory): Int = typeFactory.type(this)
}

data class HoursItem(
    val id: String,
    val hours: List<CoffeeShopHours>,
    val open: Boolean,
    val status: String?
) : ShopDetailsItem() {
    override fun isTheSame(other: Differentiable): Boolean = (id == (other as? HoursItem)?.id)
    override fun type(typeFactory: DetailsViewTypeFactory): Int = typeFactory.type(this)
}
