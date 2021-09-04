package com.skinnydoo.coffeeloc8r.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.skinnydoo.coffeeloc8r.di.qualifier.FragmentKey
import com.skinnydoo.coffeeloc8r.di.qualifier.ViewModelKey
import com.skinnydoo.coffeeloc8r.ui.details.ShopDetailsFragment
import com.skinnydoo.coffeeloc8r.ui.details.ShopDetailsViewModel
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactoryImpl
import com.skinnydoo.coffeeloc8r.ui.home.HomeFragment
import com.skinnydoo.coffeeloc8r.ui.home.HomeViewModel
import com.skinnydoo.coffeeloc8r.ui.home.PermissionFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class MainModule {

    @Binds
    @IntoMap
    @FragmentKey(PermissionFragment::class)
    abstract fun bindPermissionFragment(fragment: PermissionFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindHomeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(ShopDetailsFragment::class)
    abstract fun bindShopDetailsFragment(fragment: ShopDetailsFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(ShopDetailsViewModel::class)
    abstract fun bindShopDetailsViewModel(viewModel: ShopDetailsViewModel): ViewModel

    @Binds
    abstract fun bindDetailsViewTypeFactory(factory: DetailsViewTypeFactoryImpl): DetailsViewTypeFactory
}
