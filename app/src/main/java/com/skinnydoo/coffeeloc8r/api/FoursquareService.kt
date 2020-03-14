package com.skinnydoo.coffeeloc8r.api

import com.skinnydoo.coffeeloc8r.data.GetVenueResponse
import com.skinnydoo.coffeeloc8r.data.VenueExploreResponse
import com.skinnydoo.coffeeloc8r.data.VenueHoursResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareService {

    @GET("venues/{venueId}/hours")
    suspend fun getVenueHours(@Path("venueId") venueId: String): Response<VenueHoursResponse>

    @GET("venues/explore")
    suspend fun searchVenue(
        @Query("ll") latLng: String,
        @Query("llAcc") llAcc: String,
        @Query("query") query: String,
        @Query("sortByDistance") sortByDistance: Boolean = true,
        @Query("limit") limit: Int = 50
    ): Response<VenueExploreResponse>

    @GET("venues/{venueId}")
    suspend fun getVenue(@Path("venueId") venueId: String): Response<GetVenueResponse>
}
