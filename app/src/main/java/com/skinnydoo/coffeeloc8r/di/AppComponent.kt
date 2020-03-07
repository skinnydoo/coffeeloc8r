package com.skinnydoo.coffeeloc8r.di

import android.app.Application
import com.skinnydoo.coffeeloc8r.CoffeeLoc8rApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        LocationModule::class
    ]
)
interface AppComponent : AndroidInjector<CoffeeLoc8rApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
