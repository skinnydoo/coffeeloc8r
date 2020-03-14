package com.skinnydoo.coffeeloc8r.domain.home

import com.google.android.gms.maps.model.LatLng
import com.skinnydoo.coffeeloc8r.data.CoffeeShopRepository
import com.skinnydoo.coffeeloc8r.data.toCoffeeShop
import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.domain.home.SearchCoffeeShopUseCase.Request
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.utils.LocationUtils
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.network.Result
import com.skinnydoo.coffeeloc8r.utils.network.successOr
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCoffeeShopUseCase @Inject constructor(
    private val repository: CoffeeShopRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : UseCase<Request, List<CoffeeShop>> {

    override suspend fun invoke(req: Request): Result<List<CoffeeShop>> {
        return withContext(dispatcherProvider.io) {
            when (val result = repository.searchCoffee(req.latLng, req.accuracy)) {
                is Result.Success -> {
                    val jobs = result.data.response.groups.asSequence()
                        .flatMap { it.items.asSequence() }
                        .map {
                            async {
                                getShopDetails(
                                    it.coffeeShop.id,
                                    LocationUtils.distanceBetween(
                                        req.latLng.latitude,
                                        req.latLng.longitude,
                                        it.coffeeShop.location.lat ?: 0.0,
                                        it.coffeeShop.location.lon ?: 0.0
                                    )
                                )
                            }
                        }
                        .toList()

                    val shops = jobs.awaitAll().filterNotNull()

                    Result.Success(shops)
                }
                is Result.Error -> result
            }.exhaustive
        }
    }

    private suspend fun getShopDetails(shopId: String, distance: Double): CoffeeShop? {
        val shop = repository.getVenue(shopId).successOr(null)
        return shop?.response?.coffeeShop?.toCoffeeShop(distance)
    }

    class Request(val latLng: LatLng, val accuracy: Double)
}
