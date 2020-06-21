package com.skinnydoo.coffeeloc8r.ui.home.models

import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.ui.Actor

class HomeActor (private val emit: (HomeAction) -> Unit) : Actor {

    fun shopItemClicked(shop: CoffeeShop) = emit(HomeAction.ShowCoffeeShopDetails(shop))
}
