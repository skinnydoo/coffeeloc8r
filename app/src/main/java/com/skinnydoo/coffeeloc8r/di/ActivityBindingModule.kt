package com.skinnydoo.coffeeloc8r.di

import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import com.skinnydoo.coffeeloc8r.ui.MainActivity
import com.skinnydoo.coffeeloc8r.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

}
