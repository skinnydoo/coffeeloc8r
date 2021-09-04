package com.skinnydoo.coffeeloc8r.di.factory

import androidx.fragment.app.FragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface FragmentFactoryEntryPoint {
  fun fragmentFactory(): FragmentFactory
}
