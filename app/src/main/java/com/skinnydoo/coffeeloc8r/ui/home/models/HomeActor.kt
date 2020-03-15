package com.skinnydoo.coffeeloc8r.ui.home.models

import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.ui.Actor
import javax.inject.Inject

@PerActivity
class HomeActor @Inject constructor(private val emit: @JvmSuppressWildcards(suppress = true) (HomeAction) -> Unit) :
    Actor {

    fun shopItemClicked(shop: CoffeeShop) = emit(HomeAction.ShowCoffeeShopDetails(shop))
}
