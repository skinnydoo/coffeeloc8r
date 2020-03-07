package com.skinnydoo.coffeeloc8r

import android.os.StrictMode
import com.jakewharton.threetenabp.AndroidThreeTen
import com.skinnydoo.coffeeloc8r.di.DaggerAppComponent
import com.skinnydoo.coffeeloc8r.utils.log.MyDebugTree
import com.skinnydoo.coffeeloc8r.utils.log.ReleaseTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class CoffeeLoc8rApp : DaggerApplication() {

    override fun onCreate() {
        // ThreeTenBP for times and dates, called before super to be available for objects
        AndroidThreeTen.init(this)

        // Enable strict mode before Dagger creates graph
        if (BuildConfig.DEBUG) {
            enableStrictMode()
        }
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(MyDebugTree())
        else Timber.plant(ReleaseTree())

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
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
                .penaltyDeath()
                .build()
        )
    }

}
