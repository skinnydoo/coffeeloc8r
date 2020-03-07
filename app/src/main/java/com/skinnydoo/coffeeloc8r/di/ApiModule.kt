package com.skinnydoo.coffeeloc8r.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.skinnydoo.coffeeloc8r.BuildConfig
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.utils.network.CoffeeLoc8rRequestInterceptor
import com.skinnydoo.coffeeloc8r.di.qualifier.ApplicationContext
import com.skinnydoo.coffeeloc8r.di.qualifier.CacheDuration
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    @CacheDuration
    fun provideCacheDuration(@ApplicationContext context: Context): Int {
        return context.resources.getInteger(R.integer.cache_duration)
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        cache: Cache,
        requestInterceptor: CoffeeLoc8rRequestInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .cache(cache)
            .addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("API").d(message)
                }
            }).apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_SERVER_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    companion object {
        private const val cacheSize = 10 * 1024 * 1024L // 10MB
    }
}
