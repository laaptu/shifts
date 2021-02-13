package org.ahivs.features.shifts.actions.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.ahivs.features.shifts.R
import org.ahivs.features.shifts.actions.domain.Failure
import org.ahivs.features.shifts.actions.domain.Success
import org.ahivs.features.shifts.databinding.ActivityShiftActionBinding
import org.ahivs.shared.base.ui.ViewModelActivity
import org.ahivs.shared.base.ui.permissions.PermissionsRequest

class ShiftActionActivity : ViewModelActivity<ShiftActionViewModel>() {

    companion object {
        private val TAG = ShiftActionActivity::class.java.simpleName
        private const val START_TIME = "startTime"
        fun launchWithStartAction(context: Context): Intent =
            Intent(context, ShiftActionActivity::class.java)

        fun launchWithEndAction(context: Context, shiftStartTime: String): Intent =
            launchWithStartAction(
                context
            ).apply {
                putExtra(START_TIME, shiftStartTime)
            }

        private fun getStartTime(intent: Intent): String? = intent.getStringExtra(START_TIME)
    }

    override val viewModel: ShiftActionViewModel by viewModels { viewModelFactory }

    private val binding: ActivityShiftActionBinding by binding(R.layout.activity_shift_action)
    private var startTime: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTime = getStartTime(intent)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        requestLocationPermission()
        binding.apply {
            lifecycleOwner = this@ShiftActionActivity
            viewModel = this@ShiftActionActivity.viewModel
            btnShiftAction.setOnClickListener { invokeShiftStartEndAction() }
        }
        listenToViewModelEvents()
    }

    private fun requestLocationPermission() {
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
        viewModel.init(startTime, true)
    }

    override fun onPermissionsDenied(deniedPermissions: List<String>) {
        logger.error(TAG, "DENIED = ${Manifest.permission.ACCESS_FINE_LOCATION}")
        viewModel.init(startTime)
    }

    private fun invokeShiftStartEndAction() = viewModel.invokeShiftStartEndAction()

    private fun listenToViewModelEvents() {
        viewModel.viewState.observe(this, Observer {
            if (it is StartShift)
                supportActionBar?.title = getString(R.string.start_shift)
            else if (it is EndShift)
                supportActionBar?.title = getString(R.string.end_shift)
        })

        viewModel.shiftResponse.observe(this, Observer {
            when (it) {
                is Success -> finishWithSuccess()
                is Failure -> showMsg(it.errorMsg)
                else -> {
                }
            }
        })
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun finishWithSuccess() {
        showMsg(getString(R.string.success))
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearResources()
    }

}