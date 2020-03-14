package com.skinnydoo.coffeeloc8r.domain.details

import com.skinnydoo.coffeeloc8r.data.CoffeeShopRepository
import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.ui.details.models.DescriptionItem
import com.skinnydoo.coffeeloc8r.ui.details.models.ShopDetailsItem
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.network.Result
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class GetCoffeeShopDetailsUseCase @Inject constructor(
    private val repository: CoffeeShopRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : UseCase<GetCoffeeShopDetailsUseCase.Request, List<ShopDetailsItem>> {

    override suspend fun invoke(req: Request): Result<List<ShopDetailsItem>> {
        return withContext(dispatcherProvider.io) {
            when (val result = repository.getVenue(req.venueId)) {
                is Result.Success -> {
                    val shop = result.data.response.coffeeShop
                    val descriptionItem =
                        DescriptionItem(UUID.randomUUID().toString(), shop.description)


                    val items = listOf(descriptionItem)
                    Result.Success(items)
                }
                is Result.Error -> result
            }.exhaustive

            /*// simulate network request
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

            val description = DescriptionItem(
                id = UUID.randomUUID().toString(),
                description = "Central Park is the 843-acre green heart of Manhattan and is maintained by the Central Park Conservancy. It was designed in the 19th century by Frederick Law Olmsted and Calvert Vaux as an urban escape for New Yorkers, and now receives over 40 million visits per year."
            )

            val hours = listOf(
                CoffeeShopHours(
                    id = UUID.randomUUID().toString(),
                    day = 1,
                    opening = "8h00",
                    closing = "21h00",
                    open = true
                ),

                CoffeeShopHours(
                    id = UUID.randomUUID().toString(),
                    day = 2,
                    opening = "8h00",
                    closing = "21h00",
                    open = true
                ),

                CoffeeShopHours(
                    id = UUID.randomUUID().toString(),
                    day = 3,
                    opening = "8h00",
                    closing = "21h00",
                    open = true
                ),

                CoffeeShopHours(
                    id = UUID.randomUUID().toString(),
                    day = 4,
                    opening = "8h00",
                    closing = "21h00",
                    open = true
                )
            )

            val hoursItem = HoursItem(
                id = UUID.randomUUID().toString(),
                hours = hours,
                open = true,
                closing = "22h00"
            )
            val items = listOf(description) + mapItem + hoursItem + contactItems

            Result.Success(items)*/
        }
    }


    data class Request(val venueId: String)
}
