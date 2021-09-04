package com.skinnydoo.coffeeloc8r.domain.details

import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.data.CoffeeShopDto
import com.skinnydoo.coffeeloc8r.data.CoffeeShopRepository
import com.skinnydoo.coffeeloc8r.domain.CoroutinesDispatcherProvider
import com.skinnydoo.coffeeloc8r.domain.UseCase
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShopHours
import com.skinnydoo.coffeeloc8r.ui.details.models.*
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.network.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class GetCoffeeShopDetailsUseCase @Inject constructor(
    private val repository: CoffeeShopRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : UseCase<GetCoffeeShopDetailsUseCase.Request, Pair<String?, List<ShopDetailsItem>>> {

    override suspend fun invoke(req: Request): Result<Pair<String?, List<ShopDetailsItem>>> {
        return withContext(dispatcherProvider.io) {
            when (val result = repository.getVenue(req.venueId)) {
                is Result.Success -> {
                    val shop = result.data.response.coffeeShop
                    val descriptionItem =
                        shop.description?.let { DescriptionItem(UUID.randomUUID().toString(), it) }


                    val mapItem = MapItem(
                        id = UUID.randomUUID().toString(),
                        lat = shop.location.lat ?: 0.0,
                        lon = shop.location.lon ?: 0.0,
                        address = shop.location.formattedAddress?.joinToString(),
                        addressDetails = shop.location.crossStreet
                    )

                    val hours = getVenueHours(req.venueId)
                    val hoursItem = shop.hours?.let {
                        HoursItem(
                            id = UUID.randomUUID().toString(),
                            hours = hours,
                            open = it.isOpen,
                            status = it.status
                        )
                    }

                    val contactItems = createContactList(shop) + PoweredByItem
                    val items = listOfNotNull(descriptionItem, mapItem, hoursItem) + contactItems

                    val imageUrl = shop.bestPhoto?.let { "${it.prefix}original${it.suffix}" }

                    Result.Success(imageUrl to items)
                }
                is Result.Error -> result
            }.exhaustive
        }
    }


    private suspend fun getVenueHours(venueId: String): List<CoffeeShopHours> {
        return when (val result = repository.getVenueHours(venueId)) {
            is Result.Success -> {
                val hours = result.data.response.hours
                hours.timeFrames
                    ?.asSequence()
                    ?.map { tf ->
                        tf.days.map {
                            val open = tf.open.firstOrNull()

                            val opening = open?.start?.let {
                                LocalTime.parse(it, DateTimeFormatter.ofPattern("HHmm"))
                            }?.format(DateTimeFormatter.ofPattern("HH:mm"))

                            val closing = open?.end?.removePrefix("+")?.let {
                                LocalTime.parse(it, DateTimeFormatter.ofPattern("HHmm"))
                            }?.format(DateTimeFormatter.ofPattern("HH:mm"))

                            CoffeeShopHours(
                                id = UUID.randomUUID().toString(),
                                day = it,
                                opening = opening,
                                closing = closing,
                                open = true
                            )
                        }
                    }?.flatten()?.toList() ?: emptyList()
            }
            is Result.Error -> emptyList()
        }
    }


    private fun createContactList(shop: CoffeeShopDto): List<ContactItem> {
        return listOfNotNull(
            shop.contact?.formattedPhone?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = it,
                    type = ContactType.Phone(R.drawable.ic_phone_24_circle_brown)
                )
            },

            shop.url?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = it,
                    type = ContactType.Url(R.drawable.ic_link_24)
                )
            },

            shop.contact?.twitter?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = "https://twitter.com/${it}",
                    type = ContactType.Twitter(R.drawable.ic_link_24)
                )
            },

            shop.contact?.facebookUsername?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = "https://facebook.com/${it}",
                    type = ContactType.Facebook(R.drawable.ic_link_24)
                )
            },

            shop.contact?.instagram?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = "https://instagram.com/${it}",
                    type = ContactType.Instagram(R.drawable.ic_link_24)
                )
            },

            shop.foursquareUrl?.let {
                ContactItem(
                    id = UUID.randomUUID().toString(),
                    text = it,
                    type = ContactType.FoursquareUrl(R.drawable.ic_link_24)
                )
            }
        )

    }

    data class Request(val venueId: String)
}
