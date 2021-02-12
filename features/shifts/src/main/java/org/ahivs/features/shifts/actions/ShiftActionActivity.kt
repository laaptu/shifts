package org.ahivs.features.shifts.actions

import android.Manifest
import android.os.Bundle
import org.ahivs.features.shifts.R
import org.ahivs.shared.base.ui.permissions.PermissionActivity
import org.ahivs.shared.base.ui.permissions.PermissionsRequest
import org.ahivs.shared.base.ui.permissions.PermissionsRequestCallback

class ShiftActionActivity : PermissionActivity(), PermissionsRequestCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(
            PermissionsRequest(
                listOf(Manifest.permission.ACCESS_FINE_LOCATION),
                this,
                R.string.info_location_access,
                R.string.info_go_settings_for_location
            )
        )
    }

    override fun onPermissionsGranted() {
        println("Permission granted Yoho!!!!")
    }

    override fun onPermissionsDenied(deniedPermissions: List<String>) {
        println("Perimissions denied")
        deniedPermissions.forEach {
            println(it)
        }
    }
}