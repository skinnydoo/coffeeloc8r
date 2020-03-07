package com.skinnydoo.coffeeloc8r.utils.log

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

private const val CRASHLYTICS_KEY_PRIORITY = "priority"
private const val CRASHLYTICS_KEY_TAG = "tag"
private const val CRASHLYTICS_KEY_MESSAGE = "message"

class ReleaseTree : Timber.Tree() {
    private val crashlitics = FirebaseCrashlytics.getInstance()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.ERROR, Log.WARN -> {
                val throwable = t ?: Exception(message)

                // log crash to crashlytics
                crashlitics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
                crashlitics.setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: "")
                crashlitics.setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
                crashlitics.recordException(throwable)
            }
        }
    }

}
