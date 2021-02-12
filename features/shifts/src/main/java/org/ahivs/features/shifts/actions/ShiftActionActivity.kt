package org.ahivs.features.shifts.actions

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import org.ahivs.features.shifts.R
import org.ahivs.shared.base.ui.ViewModelActivity
import org.ahivs.shared.base.ui.permissions.PermissionsRequest

class ShiftActionActivity : ViewModelActivity<ShiftActionViewModel>() {

    companion object {
        private val TAG = ShiftActionActivity::class.java.simpleName
    }

    override val viewModel: ShiftActionViewModel by viewModels { viewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(
            PermissionsRequest(
                listOf(Manifest.permission.ACCESS_FINE_LOCATION),
                R.string.info_location_access,
                R.string.info_go_settings_for_location
            )
        )
    }

    override fun onPermissionsGranted() {
        logger.debug(TAG, "GRANTED = ${Manifest.permission.ACCESS_FINE_LOCATION}")
    }

    override fun onPermissionsDenied(deniedPermissions: List<String>) {
        logger.error(TAG, "DENIED = ${Manifest.permission.ACCESS_FINE_LOCATION}")
    }


}