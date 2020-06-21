package com.skinnydoo.coffeeloc8r.ui

import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindDetailsViewTypeFactory(factory: DetailsViewTypeFactoryImpl): DetailsViewTypeFactory
}
