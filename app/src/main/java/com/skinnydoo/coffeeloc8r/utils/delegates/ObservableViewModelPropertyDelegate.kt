package com.skinnydoo.coffeeloc8r.utils.delegates

import com.skinnydoo.coffeeloc8r.vm.ObservableViewModel
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

/**
 * A property delegate for properties of [ObservableViewModel] objects.
 */
class ObservableViewModelPropertyDelegate<T>(
  initialValue: T,
  private val observable: ObservableViewModel,
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

fun <T> ObservableViewModel.bindableProperty(
  initialValue: T,
  propertyId: Int,
  onChanged: ((oldValue: T, newValue: T) -> Unit)? = null
): ObservableViewModelPropertyDelegate<T> =
  ObservableViewModelPropertyDelegate(initialValue, this, propertyId, onChanged)
