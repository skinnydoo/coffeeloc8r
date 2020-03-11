package com.skinnydoo.coffeeloc8r.domain.details

import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.ui.details.models.*
import com.skinnydoo.coffeeloc8r.utils.network.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class GetCoffeeShopDetailsUseCase @Inject constructor(
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : UseCase<Unit, List<ShopDetailsItem>> {

    override suspend fun invoke(req: Unit): Result<List<ShopDetailsItem>> {
        return withContext(dispatcherProvider.io) {
            // simulate network request
            delay(500)

            // contact
            val contactItems = listOf(
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    type = ContactType.Phone(R.drawable.ic_phone_24_circle_brown),
                    text = "111 222 3333"
                ),

                ContactItem(
                    id = UUID.randomUUID().toString(),
                    type = ContactType.Url(R.drawable.ic_link_24),
                    text = "https://www.timhortons.ca"
                ),

                ContactItem(
                    id = UUID.randomUUID().toString(),
                    type = ContactType.Twitter(R.drawable.ic_link_24),
                    text = "https://twitter.com/tims"
                ),

                ContactItem(
                    id = UUID.randomUUID().toString(),
                    type = ContactType.FoursquareUrl(R.drawable.ic_link_24),
                    text = "https://foursquare.com/v/central-park/412d2800f964a520df0c1fe3"
                )
            ) + PoweredByItem

            val mapItem = MapItem(
                id = UUID.randomUUID().toString(),
                lat = 40.78408342593807,
                lon = -73.96485328674316,
                address = "59th St to 110th St",
                addressDetails = "5th Ave to Central Park West"
            )

            val items = listOf(mapItem) + contactItems

            Result.Success(items)
        }
    }


    data class Request(val venueId: String)
}
