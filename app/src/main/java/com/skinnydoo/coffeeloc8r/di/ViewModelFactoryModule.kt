package com.skinnydoo.coffeeloc8r.di

import androidx.lifecycle.ViewModelProvider
import com.skinnydoo.coffeeloc8r.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ViewModelFactoryModule {

    @Binds
    @ActivityScoped
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
