/*
 * ContextExt.kt
 * highchamber-android
 *
 * Created by ralph on 24/07/19 11:55 AM.
 * Last modified 24/07/19 11:55 AM.
 * Copyright (c) 2019 Guarana Technologies Inc. All rights reserved.
 */

package com.skinnydoo.coffeeloc8r.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import androidx.core.net.toUri

/**
 * Extension method to browse for Context.
 */
fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        var sanitizedUrl = url
        if (!sanitizedUrl.startsWith("www.") &&
            !sanitizedUrl.startsWith("http://") &&
            !sanitizedUrl.startsWith("https://")
        ) {
            sanitizedUrl = "www.${sanitizedUrl}"
        }

        if (!sanitizedUrl.startsWith("http://") && !sanitizedUrl.startsWith("https://")) {
            sanitizedUrl = "http://${sanitizedUrl}"
        }

        val intent = Intent(ACTION_VIEW).apply {
            data = sanitizedUrl.toUri()
            if (newTask) addFlags(FLAG_ACTIVITY_NEW_TASK)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            true
        } else false

    } catch (e: Exception) {
        false
    }

}

/**
 * Extension method to share for Context.
 */
fun Context.shareWithChooser(
    text: String,
    subject: String = "",
    chooserTitle: String = ""
): Boolean {
    val sendIntent = Intent(ACTION_SEND).apply {
        putExtra(EXTRA_SUBJECT, subject)
        putExtra(EXTRA_TEXT, text)
        type = "text/plain"
    }

    return if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(createChooser(sendIntent, chooserTitle))
        true
    } else false
}

/**
 * Extension method to share for Context.
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    val sendIntent = Intent(ACTION_SEND).apply {
        putExtra(EXTRA_SUBJECT, subject)
        putExtra(EXTRA_TEXT, text)
        type = "text/plain"
    }
    return if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(sendIntent)
        true
    } else false
}


/**
 * Extension method to dial a phone number for Context.
 * This shows the dialer with the number already entered,
 * but allows the user to decide whether to actually make the call or not.
 * It also does not require the CALL_PHONE permission.
 */
fun Context.dial(number: String): Boolean {
    return try {
        val intent = Intent(ACTION_DIAL, Uri.parse("tel:$number"))
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            true
        } else false
    } catch (e: Exception) {
        false
    }
}
