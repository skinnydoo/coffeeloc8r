package com.skinnydoo.coffeeloc8r.ui.base

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import dagger.Binds
import dagger.Module

@Module
abstract class BaseActivityModule {
  
  @Binds
  @PerActivity
  abstract fun bindActivity(appCompatActivity: AppCompatActivity): Activity
  
  @Binds
  @PerActivity
  abstract fun bindActivityContext(activity: Activity): Context
}
