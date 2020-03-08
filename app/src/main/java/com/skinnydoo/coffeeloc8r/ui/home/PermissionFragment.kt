package com.skinnydoo.coffeeloc8r.ui.home

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.skinnydoo.coffeeloc8r.BuildConfig
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.utils.PermissionsManager
import com.skinnydoo.coffeeloc8r.utils.extensions.action
import com.skinnydoo.coffeeloc8r.utils.extensions.snackBar
import timber.log.Timber
import javax.inject.Inject

private const val REQUEST_PERMISSION_REQUEST_CODE = 100
private val PERMISSION_REQUIRED = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

class PermissionFragment @Inject constructor(
    private val permissionsManager: PermissionsManager,
    private val activity: AppCompatActivity
) : Fragment() {

    private val navController by lazy { findNavController() }

    private var rationaleProvided = false

    // To display snackBars
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get a reference to the rootView
        rootView = activity.findViewById(android.R.id.content)
    }

    override fun onStart() {
        super.onStart()
        requestLocationPermission()
    }


    private fun requestLocationPermission() {
        permissionsManager.requestPermission(
            activity = activity,
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            callBack = object : PermissionsManager.PermissionsManagerListener {
                override fun onNeedPermission() {
                    Timber.d("Requesting location permission from user...")
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_REQUEST_CODE
                    )
                }

                /**
                 * Provide an additional rationale to the user. This would happen if the user denied the
                 * user denied the request previously, but didn't check the "Don't ask again" checkbox.
                 */
                override fun onPermissionPreviouslyDenied() {
                    Timber.d("Displaying permission rationale to provide additional context.")
                    showLocationRationale()
                }

                override fun onPermissionPreviouslyDeniedWithNeverAskAgain() {
                    Timber.d("Permission denied permanently...")
                    showPermissionDeniedExplanation()
                }

                override fun onPermissionGranted() {
                    Timber.d("Permission Granted...")
                    // If permissions have already been granted, proceed
                    navController.navigate(PermissionFragmentDirections.toHomeDest())
                }
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Timber.d("Permission request completed. RequestCode -> $requestCode")
        if (requestCode != REQUEST_PERMISSION_REQUEST_CODE) return

        when {
            grantResults.isEmpty() -> {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Timber.w("User interaction was cancelled")
            }
            PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull() -> {
                // permission granted
                // navigate to camera fragment
                navController.navigate(PermissionFragmentDirections.toHomeDest())
            }

            else -> { // permission denied
                if (!rationaleProvided) {
                    showLocationRationale()
                } else {
                    showPermissionDeniedExplanation()
                }

            }
        }
    }

    private fun showLocationRationale() {
        rationaleProvided = true
        MaterialDialog(activity).show {
            title(R.string.app_location_rational_title)
            message(R.string.app_location_rational_msg)
            positiveButton(android.R.string.ok) {
                // Request permission
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_REQUEST_CODE
                )
            }
            negativeButton(R.string.dialog_action_dont_allow) {
                showPermissionDeniedExplanation()
            }
        }
    }

    private fun showPermissionDeniedExplanation() {
        rootView?.snackBar(
            R.string.error_permission_denied_explanation,
            Snackbar.LENGTH_INDEFINITE
        ) {
            action(R.string.action_settings) {
                try {
                    // Build intent that displays the App settings screen.
                    val intent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                    val alternateIntent = Intent().apply {
                        action = Settings.ACTION_SETTINGS
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(alternateIntent)
                }
            }
        }
    }

    companion object {

        fun hasPermission(context: Context) =
            PERMISSION_REQUIRED.all { !PermissionsManager.shouldRequestPermission(context, it) }
    }
}
