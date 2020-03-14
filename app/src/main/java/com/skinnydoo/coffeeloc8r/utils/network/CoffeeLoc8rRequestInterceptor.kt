package com.skinnydoo.coffeeloc8r.utils.network

import com.skinnydoo.coffeeloc8r.common.AppConstants
import com.skinnydoo.coffeeloc8r.di.qualifier.CacheDuration
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CoffeeLoc8rRequestInterceptor @Inject constructor(
    @CacheDuration private val cacheDuration: Int,
    @Named(AppConstants.KEY_FOURSQUARE_CLIENT_ID) private val clientId: String,
    @Named(AppConstants.KEY_FOURSQUARE_CLIENT_SECRET) private val clientSecret: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url
            .newBuilder()
            .addQueryParameter("client_id", clientId)
            .addQueryParameter("client_secret", clientSecret)
            .addQueryParameter("v", "20180323")
            .build()

        val newRequest =
            original.newBuilder()
                .url(url)
                .addHeader("User-Agent", "CoffeeLoc8r")
                .addHeader("X-Request-ID", UUID.randomUUID().toString())
                .addHeader("Cache-Control", "public, max-age=$cacheDuration")
                .build()

        return chain.proceed(newRequest)
    }
}
