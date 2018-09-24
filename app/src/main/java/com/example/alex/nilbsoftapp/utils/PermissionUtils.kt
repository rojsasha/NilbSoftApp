package com.example.alex.nilbsoftapp.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager

import android.content.Context
import android.support.v4.app.ActivityCompat
import android.location.LocationManager

class PermissionUtils {
    companion object {
        fun checkLocationPermission(activity: Activity): Boolean {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (isLocationPermissionGranted(activity)) return true

            ActivityCompat.requestPermissions(activity, permission, AppConstants.REQUEST_CODE_LOCATION_PERMISSION)
            return false
        }

        private fun isLocationPermissionGranted(context: Context): Boolean {
            return ActivityCompat
                    .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
    }
}