package com.skinnydoo.coffeeloc8r.ui.home.models

import com.skinnydoo.coffeeloc8r.domain.home.CoffeeShop

sealed class HomeAction {
    data class ShowCoffeeShopDetails(val shop: CoffeeShop): HomeAction()
}
