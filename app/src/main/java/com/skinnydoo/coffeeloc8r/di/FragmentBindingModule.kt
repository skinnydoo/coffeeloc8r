package com.skinnydoo.coffeeloc8r.di

import androidx.fragment.app.FragmentFactory
import com.skinnydoo.coffeeloc8r.di.factory.FragmentInjectionFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FragmentBindingModule {

    @Binds
    abstract fun bindFragmentFactory(factory: FragmentInjectionFactory): FragmentFactory
}
