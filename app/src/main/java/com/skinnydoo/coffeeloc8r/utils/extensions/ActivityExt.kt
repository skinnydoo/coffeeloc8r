package com.skinnydoo.coffeeloc8r.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : Activity> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

inline fun <reified T : Activity> Context.startActivity() = startActivity(
    newIntent<T>(
        this
    )
)

inline fun <reified T : Activity> Context.startActivity(
    options: Bundle? = null,
    noinline fn: Intent.() -> Unit
) {
    val intent = newIntent<T>(this)
    intent.fn()
    startActivity(intent, options)
}

inline fun <reified T : Activity> AppCompatActivity.openActivityForResult(
    requestCode: Int, noinline
    fn: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.fn()
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory)[T::class.java]
}
