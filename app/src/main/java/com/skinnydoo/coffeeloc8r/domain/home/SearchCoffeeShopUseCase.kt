package com.skinnydoo.coffeeloc8r.domain.home

import com.google.android.gms.maps.model.LatLng
import com.skinnydoo.coffeeloc8r.data.CoffeeShopRepository
import com.skinnydoo.coffeeloc8r.data.toCoffeeShop
import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.domain.home.SearchCoffeeShopUseCase.Request
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.network.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class SearchCoffeeShopUseCase @Inject constructor(
  private val repository: CoffeeShopRepository,
  private val dispatcherProvider: CoroutinesDispatcherProvider
) : UseCase<Request, List<CoffeeShop>> {

  override suspend fun invoke(req: Request): Result<List<CoffeeShop>> {
    return withContext(dispatcherProvider.io) {
      when (val result = repository.searchCoffee(req.latLng, req.accuracy)) {
        is Result.Success -> {
          val shops = result.data.response.groups.asSequence()
            .flatMap { it.items.asSequence() }
            .map { it.coffeeShop.toCoffeeShop() }
            .toList()

          Result.Success(shops)
        }
        is Result.Error -> result
      }.exhaustive
    }
  }

  class Request(val latLng: LatLng, val accuracy: Double)
}
