package com.skinnydoo.coffeeloc8r.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.skinnydoo.coffeeloc8r.di.scope.PerActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@PerActivity
class FragmentInjectionFactory @Inject constructor(
  private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
): FragmentFactory() {

  override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
    val fragmentClass = loadFragmentClass(classLoader, className)
    val found = creators.entries.firstOrNull { fragmentClass.isAssignableFrom(it.key) }
    val creator = found?.value ?: return createFragmentAsFallback(classLoader, className)

    try {
      return creator.get()
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  /**
   *  Rely on the default implementation to handles Fragments
   *  without an @Inject annotated constructor.
   */
  private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
    Timber.w("No creator found for class: $className. Using default constructor")
    return super.instantiate(classLoader, className)
  }
}
