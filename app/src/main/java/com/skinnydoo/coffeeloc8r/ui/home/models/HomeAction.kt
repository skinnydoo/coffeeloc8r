package com.skinnydoo.coffeeloc8r.ui.home.models

import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop

sealed class HomeAction {
    data class ShowCoffeeShopDetails(val shop: CoffeeShop): HomeAction()
}
