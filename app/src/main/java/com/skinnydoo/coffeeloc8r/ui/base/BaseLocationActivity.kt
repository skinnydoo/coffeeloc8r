package com.skinnydoo.coffeeloc8r.ui.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.skinnydoo.coffeeloc8r.utils.PermissionsManager
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseLocationActivity : BaseDaggerActivity() {

    // Provides access to the Fused Location Provider API
    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    // Provides access tot the Location Settings API
    @Inject
    lateinit var settingsClient: SettingsClient

    // Stores parameters for requests tot the FusedLocationProviderApo
    @Inject
    lateinit var locationRequest: LocationRequest

    // Used to determine if the devices has optimal location settings
    @Inject
    lateinit var locationSettingsRequest: LocationSettingsRequest

    // Callback for location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationCallbackStrongRef: LocationCallback

    @Inject
    lateinit var permissionsManager: PermissionsManager

    private var rationaleProvided = false

    // To display snackBars
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get a reference to the rootView
        rootView = findViewById(android.R.id.content)
        createLocationCallback()
    }


    override fun onPause() {
        super.onPause()
        // Remove location updates to save battery
        stopLocationUpdates()
    }

    /**
     * Return the current state of the permissions needed
     */
    protected fun shouldRequestPermissions(): Boolean {
        return permissionsManager.shouldRequestPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    /**
     * Delivers location result
     */
    protected abstract fun onLocationResult(result: Location?)

    protected open fun onPermissionGranted() {}


    /**
     * Create a callback for receiving location homeViewEvents
     */
    private fun createLocationCallback() {
        locationCallbackStrongRef = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                when {
                    locationResult != null -> this@BaseLocationActivity.onLocationResult(
                        locationResult.lastLocation
                    )
                    else -> {
                        // appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location)
                        this@BaseLocationActivity.onLocationResult(null)
                    }
                }
            }
        }
        locationCallback = LocationCallBackRef(locationCallbackStrongRef)
    }


    protected fun requestLocationPermission() {
        Timber.d("Requesting location permission from the user...")
        permissionsManager.requestPermission(
            activity = this,
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            callBack = object : PermissionsManager.PermissionsManagerListener {

                override fun onNeedPermission() {
                    Timber.d("Requesting permission...")
                    ActivityCompat.requestPermissions(
                        this@BaseLocationActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSIONS_REQUEST_CODE
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
                    onLocationResult(null)
                }

                override fun onPermissionGranted() {
                    Timber.d("Permission Granted...")
                    requestLocationUpdates()
                    this@BaseLocationActivity.onPermissionGranted()
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        Timber.d("Permission request completed. RequestCode -> $requestCode")
        if (requestCode != REQUEST_PERMISSIONS_REQUEST_CODE) return

        when {
            grantResults.isEmpty() -> {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Timber.w("User interaction was cancelled")
            }
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                // Permission granted...start location updates
                requestLocationUpdates()
                onPermissionGranted()
            }
            else -> { // Permission denied
                if (!rationaleProvided) {
                    showLocationRationale()
                } else {
                    onLocationResult(null)
                }
            }

        }
    }

    private fun showLocationRationale() {
        rationaleProvided = true
        /*MaterialDialog(this).show {
          title(R.string.app_would_like_location)
          message(R.string.to_show_stores_around_your_location)
          positiveButton(android.R.string.ok) {
            // Request permission
            ActivityCompat.requestPermissions(
              this@BaseLocationActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
              REQUEST_PERMISSIONS_REQUEST_CODE
            )
          }
          negativeButton(R.string.dialog_action_dont_allow) {
            onLocationResult(null)
          }
        }*/
    }

    private fun showPermissionDeniedExplanation() {
        //rootView?.snackBar(R.string.error_permission_denied_explanation, Snackbar.LENGTH_LONG)
        //showToast(R.string.error_permission_denied_explanation, Toast.LENGTH_LONG)
        onLocationResult(null)
    }

    /**
     * Request location updates from the FusedLocationApi.
     * Note: This is not called unless location runtime permission has been granted
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        Timber.d("Starting location updates...")
        Timber.d("Checking if the device has the necessary location settings...")
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                Timber.d("Settings OK. Requesting location update...")

                // Request location updates
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )

                Timber.d("Location update requested.")
            }
            .addOnFailureListener {
                Timber.w("Device doesn't have the best location settings...")
                if (it !is ApiException) return@addOnFailureListener

                when (it.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Timber.d("Attempting to upgrade location settings")
                        // Attempting to upgrade location settings
                        try {
                            // Show the dialog by calling startResolutionForResult(), and
                            // check the result in onActivityResult()
                            val rae = it as ResolvableApiException
                            rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                        } catch (ex: IntentSender.SendIntentException) {
                            Timber.e(ex, "PendingIntent unable to execute request")
                            //appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location)
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        //Timber.e(getString(R.string.error_location_settings_inadequate))
                        //appNavigator.showErrorDialog(msgRes = R.string.error_location_settings_inadequate)
                    }
                }
            }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     * It is good practice to remove location request when the activity is in a paused or stopped
     * state. Doing so helps battery performance.
     */
    private fun stopLocationUpdates() {
        Timber.d("Stopping location updates...")
        fusedLocationClient.removeLocationUpdates(locationCallback)
            .addOnCompleteListener {
                Timber.d("Location updates stopped")
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("Activity result completed. RequestCode -> $requestCode")
        if (requestCode != REQUEST_CHECK_SETTINGS) return

        when (resultCode) {
            Activity.RESULT_OK -> {
                // Nothing to do. requestLocationUpdates gets called in onStart again
                Timber.d("User agreed to make required location settings changes")
                requestLocationUpdates()
            }
            Activity.RESULT_CANCELED -> {
                // User chose not to make the required location settings changes
                Timber.d("User chose not to make required location settings changes")
                //appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // WorkAround to avoid activity leak
// issue: https://issuetracker.google.com/issues/37126862
    class LocationCallBackRef(locationCallback: LocationCallback) : LocationCallback() {
        private val callbackRef: WeakReference<LocationCallback> = WeakReference(locationCallback)

        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            callbackRef.get()?.onLocationResult(locationResult)
        }
    }

    companion object {
        //region Permission request code

        // constant used in requesting runtime permissions
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 100

        // constant used in the location settings dialog
        private const val REQUEST_CHECK_SETTINGS = 101

        //endregion
    }
}
