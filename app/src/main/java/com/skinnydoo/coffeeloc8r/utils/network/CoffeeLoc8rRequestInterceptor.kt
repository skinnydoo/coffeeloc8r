package com.skinnydoo.coffeeloc8r.utils.network

import com.skinnydoo.coffeeloc8r.di.qualifier.CacheDuration
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeLoc8rRequestInterceptor @Inject constructor(
   @CacheDuration private val cacheDuration: Int
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val newRequest =
            original.newBuilder()
                .addHeader("User-Agent", "CoffeeLoc8r")
                .addHeader("X-Request-ID", UUID.randomUUID().toString())
                .addHeader("Cache-Control", "public, max-age=$cacheDuration")
                .build()

        return chain.proceed(newRequest)
    }
}
