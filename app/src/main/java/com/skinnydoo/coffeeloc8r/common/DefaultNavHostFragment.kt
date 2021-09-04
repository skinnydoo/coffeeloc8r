package com.skinnydoo.coffeeloc8r.common

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DefaultNavHostFragment : NavHostFragment() {

  @Inject
  lateinit var fragmentFactory: FragmentFactory

  override fun onAttach(context: Context) {
    super.onAttach(context)
    childFragmentManager.fragmentFactory = fragmentFactory
  }
}
