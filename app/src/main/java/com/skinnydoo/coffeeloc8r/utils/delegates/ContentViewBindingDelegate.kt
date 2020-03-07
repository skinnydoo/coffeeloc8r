package com.skinnydoo.coffeeloc8r.utils.delegates

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A delegate who lazily inflates a data binding layout, calls [Activity.setContentView] and returns
 * the binding.
 */
class ContentViewBindingDelegate<out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<Activity, T> {

    private var binding: T? = null

    override fun getValue(
        thisRef: Activity,
        property: KProperty<*>
    ): T = binding ?: createBinding(thisRef).also { binding = it }

    private fun createBinding(
        activity: Activity
    ): T = DataBindingUtil.setContentView(activity, layoutRes)

}

/**
 * Activity bindings
 */
fun <T : ViewDataBinding> contentView(
    @LayoutRes layoutRes: Int
): ContentViewBindingDelegate<T> = ContentViewBindingDelegate(layoutRes)
