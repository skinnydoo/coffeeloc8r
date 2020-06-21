package com.skinnydoo.coffeeloc8r.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.skinnydoo.coffeeloc8r.BuildConfig
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppConstants
import com.skinnydoo.coffeeloc8r.databinding.ActivityMainBinding
import com.skinnydoo.coffeeloc8r.utils.delegates.contentView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val binding by contentView<ActivityMainBinding>(R.layout.activity_main)
  private val viewModel by viewModels<MainViewModel>()

  private lateinit var navController: NavController

    private lateinit var appUpdateManager: AppUpdateManager
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.lifecycleOwner = this
    binding.vm = viewModel
      appUpdateManager = AppUpdateManagerFactory.create(this)
    navController = findNavController(R.id.nav_host_main)
    initView()
      requestUpdate()
  }

    override fun onResume() {
        super.onResume()
        checkAppUpdateInProgress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("RequestCode/ResultCode (Cancelled:0, Ok:-1) -> $requestCode/$resultCode")
        when (requestCode) {
            AppConstants.REQUEST_IMMEDIATE_UPDATE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        // handle user's approval
                        // For immediate updates, we might not receive this callback because the update should already
                        // be completed by Google Play by the time the control is given back to the app.
                    }
                    Activity.RESULT_CANCELED -> {
                        // Handle user's rejection
                        Timber.w("Update flow cancelled! Result code -> $resultCode")
                        requestUpdate() // request update again
                    }
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                        // Handle update failure
                        Timber.w("Update flow failed! Result code -> $resultCode")
                        requestUpdate() // request update again
                    }
                }
            }
        }
    }

  private fun initView() {
    setUpListeners()
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

    private fun requestUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                Timber.d("Update available, starting the update")
                startInAppUpdate(it)
            }
        }
    }

    private fun startInAppUpdate(appUpdateInfo: AppUpdateInfo?) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo, AppUpdateType.IMMEDIATE, this, AppConstants.REQUEST_IMMEDIATE_UPDATE
        )
    }

    private fun checkAppUpdateInProgress() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                Timber.i("In-app update is already running, resuming the update")
                startInAppUpdate(it)
            }
        }
    }
}
