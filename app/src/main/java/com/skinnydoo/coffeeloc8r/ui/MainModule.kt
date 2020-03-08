package com.skinnydoo.coffeeloc8r.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.skinnydoo.coffeeloc8r.di.FragmentBindingModule
import com.skinnydoo.coffeeloc8r.di.qualifier.FragmentKey
import com.skinnydoo.coffeeloc8r.di.qualifier.ViewModelKey
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.base.BaseActivityModule
import com.skinnydoo.coffeeloc8r.ui.home.HomeFragment
import com.skinnydoo.coffeeloc8r.ui.home.HomeViewModel
import com.skinnydoo.coffeeloc8r.ui.home.PermissionFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [BaseActivityModule::class, FragmentBindingModule::class])
abstract class MainModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindHomeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(PermissionFragment::class)
    abstract fun bindPermissionFragment(fragment: PermissionFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @PerActivity
    abstract fun bindMainActivity(activity: MainActivity): AppCompatActivity
}
