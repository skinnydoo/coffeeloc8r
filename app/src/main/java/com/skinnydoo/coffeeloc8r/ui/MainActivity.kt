package com.skinnydoo.coffeeloc8r.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.skinnydoo.coffeeloc8r.BuildConfig
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.databinding.ActivityMainBinding
import com.skinnydoo.coffeeloc8r.ui.base.BaseFragmentFactoryActivity
import com.skinnydoo.coffeeloc8r.utils.delegates.contentView
import timber.log.Timber

class MainActivity : BaseFragmentFactoryActivity() {

  private val binding by contentView<ActivityMainBinding>(R.layout.activity_main)
  private val viewModel by viewModels<MainViewModel> { viewModelFactory }

  private lateinit var navController: NavController
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.lifecycleOwner = this
    binding.vm = viewModel
    navController = findNavController(R.id.nav_host_main)
    initView()
  }

  private fun initView() {
    setupToolbar()
    setUpListeners()
  }

  private fun setupToolbar() {
    setSupportActionBar(binding.toolbarInclude.toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
  }

  private fun setUpListeners() {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      if (BuildConfig.DEBUG) logDestination(destination)
    }
  }

  private fun logDestination(destination: NavDestination) {
    val dest: String = try {
      resources.getResourceName(destination.id)
    } catch (e: Resources.NotFoundException) {
      destination.id.toString()
    }
    Timber.d("Navigated to $dest")
  }
}
