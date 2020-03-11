package com.skinnydoo.coffeeloc8r.domain.home

import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.utils.network.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class SearchCoffeeShopUseCase @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider
): UseCase<Unit, List<CoffeeShop>> {

    override suspend fun invoke(req: Unit): Result<List<CoffeeShop>> {
        return withContext(dispatcherProvider.io) {
            // simulate network request
            delay(1_500)

            val list = listOf(
                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Tim Hortons",
                    open = true,
                    distance = 2.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Van Houtte",
                    open = true,
                    distance = 9.0,
                    isFavorite = true
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Starbucks",
                    open = false,
                    distance = 4.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "McDz",
                    open = true,
                    distance = 25.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Tim Hortons",
                    open = true,
                    distance = 2.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "U & Me Coffee Bar",
                    open = false,
                    distance = 5.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "U & Me Coffee Bar",
                    open = true,
                    distance = 12.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "The Coffee Bar",
                    open = true,
                    distance = 5.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Coffee Bar",
                    open = true,
                    distance = .5
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Code Black Coffee Bar",
                    open = false,
                    distance = 15.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Miss Coffee",
                    open = true,
                    distance = 5.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Starbucks",
                    open = true,
                    distance = 10.0,
                    isFavorite = true
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "U & Me Coffee Bar",
                    open = true,
                    distance = 7.0
                ),

                CoffeeShop(
                    id = UUID.randomUUID().toString(),
                    name = "Coffee Bar",
                    open = true,
                    distance = 5.0
                )
            )


            Result.Success(list)
        }
    }
}
