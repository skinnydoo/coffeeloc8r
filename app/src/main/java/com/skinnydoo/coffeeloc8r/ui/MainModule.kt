package com.skinnydoo.coffeeloc8r.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinnydoo.coffeeloc8r.di.FragmentBindingModule
import com.skinnydoo.coffeeloc8r.di.qualifier.FragmentKey
import com.skinnydoo.coffeeloc8r.di.qualifier.ViewModelKey
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.base.BaseActivityModule
import com.skinnydoo.coffeeloc8r.ui.details.ShopDetailsFragment
import com.skinnydoo.coffeeloc8r.ui.details.ShopDetailsViewModel
import com.skinnydoo.coffeeloc8r.ui.details.models.DetailsAction
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactory
import com.skinnydoo.coffeeloc8r.ui.details.models.viewtype.DetailsViewTypeFactoryImpl
import com.skinnydoo.coffeeloc8r.ui.home.HomeFragment
import com.skinnydoo.coffeeloc8r.ui.home.HomeViewModel
import com.skinnydoo.coffeeloc8r.ui.home.PermissionFragment
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeAction
import com.skinnydoo.coffeeloc8r.utils.extensions.obtainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [BaseActivityModule::class, FragmentBindingModule::class])
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
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @PerActivity
    abstract fun bindDetailsViewTypeFactory(factory: DetailsViewTypeFactoryImpl): DetailsViewTypeFactory

    @Binds
    @PerActivity
    abstract fun bindMainActivity(activity: MainActivity): AppCompatActivity

    companion object {
        @Provides
        fun provideHomeAction(
            activity: AppCompatActivity,
            factory: ViewModelProvider.Factory
        ): (HomeAction) -> Unit {
            val viewModel = activity.obtainViewModel<MainViewModel>(factory)
            return viewModel::emitHomeAction
        }

        @Provides
        fun provideDetailsAction(
            activity: AppCompatActivity,
            factory: ViewModelProvider.Factory
        ): (DetailsAction) -> Unit {
            val viewModel = activity.obtainViewModel<MainViewModel>(factory)
            return viewModel::emitDetailsViewAction
        }
    }
}
