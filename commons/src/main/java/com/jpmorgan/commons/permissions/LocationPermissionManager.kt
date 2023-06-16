package com.jpmorgan.commons.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class LocationPermissionManager(private val fragment: Fragment,
                                private var onPermissionGranted: (() -> Unit)? = null,
                                private var onPermissionDenied: (() -> Unit)? = null) {

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    private val requestPermissionLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                this.onPermissionGranted?.invoke()
            } else {
                this.onPermissionDenied?.invoke()
            }
        }

    fun hasPermission(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(
            fragment.requireActivity(),
            locationPermission
        )
        return permissionStatus == PackageManager.PERMISSION_GRANTED

    }

    fun requestPermission() {
        requestPermissionLauncher.launch(locationPermission)
    }


    fun onPermissionGranted(action: () -> Unit) {
        this.onPermissionGranted = action
    }


    fun onPermissionDenied(action: () -> Unit) {
        this.onPermissionDenied = action
    }
}

