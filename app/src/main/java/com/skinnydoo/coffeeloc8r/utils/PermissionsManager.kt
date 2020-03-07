package com.skinnydoo.coffeeloc8r.utils

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.skinnydoo.coffeeloc8r.utils.extensions.applyMe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionsManager @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun shouldRequestPermission(context: Context, permission: String): Boolean {
        if (shouldRequestPermission()) {
            val permissionState = ContextCompat.checkSelfPermission(context, permission)
            return permissionState != PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    fun requestPermission(activity: AppCompatActivity, permission: String, callBack: PermissionsManagerListener) {
        if (shouldRequestPermission(activity, permission)) {

            val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            )
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            if (shouldProvideRationale) {
                callBack.onPermissionPreviouslyDenied() // Provide rationale
            } else {
                if(isFirstTimeRequesting(permission)) {
                    firstTimeRequesting(permission, false)
                    callBack.onNeedPermission() // Request Permission.
                } else {
                    //Permission disable by device policy or user denied permanently. Show proper error message
                    callBack.onPermissionPreviouslyDeniedWithNeverAskAgain()
                }
            }

        } else {
            // permission granted. do your stuff
            callBack.onPermissionGranted()
        }
    }

    private fun shouldRequestPermission(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M


    private fun isFirstTimeRequesting(permission: String) = prefs.getBoolean(permission, true)

    private fun firstTimeRequesting(permission: String, isFirstTime: Boolean) {
        prefs.applyMe { putBoolean(permission, isFirstTime) }
    }

    interface PermissionsManagerListener {
        fun onNeedPermission()
        fun onPermissionPreviouslyDenied()
        fun onPermissionPreviouslyDeniedWithNeverAskAgain()
        fun onPermissionGranted()
    }
}
