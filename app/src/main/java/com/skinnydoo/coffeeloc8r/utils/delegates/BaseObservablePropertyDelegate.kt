package com.skinnydoo.coffeeloc8r.utils.delegates

import androidx.databinding.BaseObservable
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

/**
 * A property delegate for properties of [BaseObservable] objects.
 */
class BaseObservablePropertyDelegate<T>(
    initialValue: T,
    private val observable: BaseObservable,
    private val propertyId: Int,
    private val onChanged: ((oldValue: T, newValue: T) -> Unit)? = null
) : ObservableProperty<T>(initialValue) {

    override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean {
        // Avoid Infinite Loop
        return oldValue != newValue
    }

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
        // React to the change.
        onChanged?.invoke(oldValue, newValue)
        // Notify observers of a new value.
        observable.notifyPropertyChanged(propertyId)
    }
}

fun <T> BaseObservable.bindableProperty(
    initialValue: T,
    propertyId: Int,
    onChanged: ((oldValue: T, newValue: T) -> Unit)? = null
): BaseObservablePropertyDelegate<T> =
    BaseObservablePropertyDelegate(initialValue, this, propertyId, onChanged)
