package com.skinnydoo.coffeeloc8r.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.showToast(@StringRes actionRes: Int, duration: Int = Toast.LENGTH_LONG) {
    showToast(resources.getString(actionRes), duration)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}


fun Fragment.showToast(@StringRes actionRes: Int, duration: Int = Toast.LENGTH_LONG) {
    showToast(resources.getString(actionRes), duration)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, duration).show()
}

