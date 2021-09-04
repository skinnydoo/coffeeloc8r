package com.skinnydoo.coffeeloc8r.di

import androidx.fragment.app.FragmentFactory
import com.skinnydoo.coffeeloc8r.di.factory.FragmentInjectionFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class FragmentFactoryModule {

    @Binds
    @ActivityScoped
    abstract fun bindFragmentFactory(factory: FragmentInjectionFactory): FragmentFactory
}
