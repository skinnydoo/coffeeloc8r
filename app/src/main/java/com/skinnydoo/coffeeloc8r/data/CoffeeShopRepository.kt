package com.skinnydoo.coffeeloc8r.data

import com.google.android.gms.maps.model.LatLng
import com.skinnydoo.coffeeloc8r.utils.network.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeShopRepository @Inject constructor(
    private val dataSource: CoffeeShopRemoteDataSource
) {
    suspend fun searchCoffee(latLng: LatLng, accuracy: Double): Result<VenueExploreResponse> {
        return dataSource.searchCoffeeShops(latLng, accuracy)
    }

    suspend fun getVenueHours(venueId: String) = dataSource.getVenueHours(venueId)

    suspend fun getVenue(venueId: String) = dataSource.getVenue(venueId)
}
