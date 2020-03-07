package com.skinnydoo.coffeeloc8r.utils.log

import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${super.createStackElementTag(element)}:${element.lineNumber}@${element.methodName}"
    }
}
