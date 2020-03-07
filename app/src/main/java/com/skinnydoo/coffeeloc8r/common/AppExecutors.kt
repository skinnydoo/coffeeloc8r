package com.skinnydoo.coffeeloc8r.common

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Global executor pools for the whole application.
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind webservice requests).
 */
@Singleton
open class AppExecutors(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    /**
     * Utility method to run blocks on a dedicated background thread.
     * Used for disk io/database work.
     */
    fun diskIO(fn: () -> Unit) = diskIO.execute(fn)

    /**
     * Utility method to run blocks on dedicated background threads.
     * Used for network IO
     */
    fun networkIO(fn: () -> Unit) = networkIO.execute(fn)

    /**
     * Utility method to run blocks on the UI thread
     */
    fun mainThread(fn: () -> Unit) = mainThread.execute(fn)

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
