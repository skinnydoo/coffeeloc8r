package com.skinnydoo.coffeeloc8r

import android.app.Application
import android.os.StrictMode
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.skinnydoo.coffeeloc8r.utils.log.MyDebugTree
import com.skinnydoo.coffeeloc8r.utils.log.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CoffeeLoc8rApp : Application() {

  override fun onCreate() {
    // Enable strict mode before Dagger creates graph
    if (BuildConfig.DEBUG) {
      enableStrictMode()
    }
    super.onCreate()

    setUpFirebaseCrashlytics()
    if (BuildConfig.DEBUG) Timber.plant(MyDebugTree())
    else Timber.plant(ReleaseTree())

  }

  private fun enableStrictMode() {
    // Thread Policy
    StrictMode.setThreadPolicy(
      StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()
        .penaltyLog()
        .build()
    )

    // Vm Policy
    StrictMode.setVmPolicy(
      StrictMode.VmPolicy.Builder()
        .detectActivityLeaks()
        .detectLeakedClosableObjects()
        .detectLeakedSqlLiteObjects()
        .penaltyLog()
        .build()
    )
  }

  private fun setUpFirebaseCrashlytics() {
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
  }

}
