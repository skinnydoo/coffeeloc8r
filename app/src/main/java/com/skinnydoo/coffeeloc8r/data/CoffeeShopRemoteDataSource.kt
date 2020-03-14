package com.skinnydoo.coffeeloc8r.data

import com.google.android.gms.maps.model.LatLng
import com.skinnydoo.coffeeloc8r.api.FoursquareService
import com.skinnydoo.coffeeloc8r.di.qualifier.CacheDuration
import com.skinnydoo.coffeeloc8r.utils.network.Result
import com.skinnydoo.coffeeloc8r.utils.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeShopRemoteDataSource @Inject constructor(
    @CacheDuration private val cacheDuration: Int,
    private val foursquareService: FoursquareService
) {

    suspend fun searchCoffeeShops(latLng: LatLng, accuracy: Double) = safeApiCall(
        call = { requestSearchCoffeeShops(latLng, accuracy) },
        errorMessage = "An error occurred"
    )

    private suspend fun requestSearchCoffeeShops(
        latLng: LatLng,
        accuracy: Double
    ): Result<VenueExploreResponse> {
        val response =
            foursquareService.searchVenue(
                latLng = "${latLng.latitude},${latLng.longitude}",
                llAcc = accuracy.toString(),
                query = "coffee"
            )

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Result.Success(body)
        }
        return Result.Error(Exception("search coffee shop failed: code=${response.code()}, message=${response.message()}"))
    }

    suspend fun getVenueHours(venueId: String) = safeApiCall(
        call = { requestGetVenueHours(venueId) },
        errorMessage = "Unable to retrieve hours for venue with id $venueId"
    )


    private suspend fun requestGetVenueHours(venueId: String): Result<VenueHoursResponse> {
        val response = foursquareService.getVenueHours(venueId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Result.Success(body)
        }
        return Result.Error(Exception("Failed to get venue hours: code=${response.code()}, message=${response.message()}"))
    }

    suspend fun getVenue(venueId: String) = safeApiCall(
        call = { requestGetVenue(venueId) },
        errorMessage = "Failed to get details for venue $venueId"
    )

    private suspend fun requestGetVenue(venueId: String): Result<GetVenueResponse> {
        val response = foursquareService.getVenue(venueId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Result.Success(body)
        }
        return Result.Error(Exception("Failed to get venue: code=${response.code()}, message=${response.message()}"))
    }
}
