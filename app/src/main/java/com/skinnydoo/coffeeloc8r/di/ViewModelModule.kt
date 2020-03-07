package com.skinnydoo.coffeeloc8r.di

import androidx.lifecycle.ViewModelProvider
import com.skinnydoo.coffeeloc8r.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
