package com.skinnydoo.coffeeloc8r.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.core.os.ConfigurationCompat
import com.skinnydoo.coffeeloc8r.common.AppConstants
import com.skinnydoo.coffeeloc8r.di.qualifier.ApplicationContext
import com.skinnydoo.coffeeloc8r.di.qualifier.LocaleLang
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(app: Application): Context = app

    @Singleton
    @Provides
    fun provideResources(app: Application): Resources = app.resources

    @Provides
    @LocaleLang
    fun provideLocaleLang(app: Application): String {
        val currentLocale = ConfigurationCompat.getLocales(app.resources.configuration)[0]
        return currentLocale.language
    }

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(AppConstants.SP_NAME, Context.MODE_PRIVATE)
    }
}
