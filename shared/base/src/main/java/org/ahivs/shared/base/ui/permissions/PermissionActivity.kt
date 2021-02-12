package org.ahivs.shared.base.ui.permissions

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.ahivs.shared.base.R
import org.ahivs.shared.base.ui.BaseActivity

abstract class PermissionActivity : BaseActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        const val REQUEST_PERMISSION = 0x1
        const val REQUEST_SETTINGS = 0x2
    }

    private var permissionsRequest: PermissionsRequest? = null


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val deniedPermissions = getDeniedPermissions(permissions.toList())
        if (deniedPermissions.isEmpty()) {
            notifySuccess()
        } else {
            showPermissionsDeniedAlert(deniedPermissions)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun requestPermissions(permissionsRequest: PermissionsRequest) {
        if (this.permissionsRequest != null) {
            //there is some permission request going on
            return
        }
        this.permissionsRequest = permissionsRequest
        val deniedPermissions = getDeniedPermissions(permissionsRequest.permissions)
        when {
            deniedPermissions.isEmpty() -> notifySuccess()

            shouldRationaleMsgBeDisplayed(permissionsRequest.rationalMsgId, deniedPermissions) ->
                showRationaleAlert(
                    deniedPermissions,
                    getString(permissionsRequest.rationalMsgId)
                )

            else -> requestPermissions(deniedPermissions)
        }
    }

    private fun shouldRationaleMsgBeDisplayed(
        rationaleMsgId: Int,
        deniedPermissions: List<String>
    ): Boolean {
        if (rationaleMsgId == 0)
            return false
        deniedPermissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, it))
                return true
        }
        return false
    }

    private fun requestPermissions(neededPermissions: List<String>) {
        ActivityCompat.requestPermissions(
            this, neededPermissions.toTypedArray(),
            REQUEST_PERMISSION
        )
    }

    private fun showRationaleAlert(deniedPermissions: List<String>, rationaleMsg: String) {
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setMessage(rationaleMsg)
            .setCancelable(false)
            .setNegativeButton(
                getString(R.string.proceed)
            ) { _, _ -> requestPermissions(deniedPermissions) }
            .show()
    }

    private fun showPermissionsDeniedAlert(deniedPermissions: List<String>) {
        permissionsRequest?.let {
            if (it.deniedMsgId == 0)
                notifyFailure(deniedPermissions)
            else {
                MaterialAlertDialogBuilder(
                    this,
                    R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
                )
                    .setMessage(getString(it.deniedMsgId))
                    .setCancelable(false)
                    .setNegativeButton(
                        getString(R.string.cancel)
                    ) { _, _ -> notifyFailure(deniedPermissions) }
                    .setPositiveButton(
                        getString(R.string.settings)
                    ) { _, _ -> startSettings() }
                    .show()
            }
        }
    }

    private fun startSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:$packageName"))
        startActivityForResult(intent, REQUEST_SETTINGS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SETTINGS) {
            permissionsRequest?.let {
                val deniedPermission = getDeniedPermissions(it.permissions)
                if (deniedPermission.isEmpty())
                    notifySuccess()
                else
                    notifyFailure(deniedPermission)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun notifySuccess() {
        permissionsRequest?.apply {
            onPermissionsGranted()
        }
        clearRequest()
    }

    private fun notifyFailure(deniedPermissions: List<String>) {
        permissionsRequest?.apply {
            onPermissionsDenied(deniedPermissions)
        }
        clearRequest()
    }

    private fun clearRequest() {
        permissionsRequest = null;
    }

    private fun getDeniedPermissions(permissions: List<String>) =
        permissions.filter {
            !isPermissionGranted(it)
        }

    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    open fun onPermissionsGranted(){

    }
    open fun onPermissionsDenied(deniedPermissions: List<String>){

    }
}

class PermissionsRequest(
    val permissions: List<String>,
    val rationalMsgId: Int = 0,
    val deniedMsgId: Int = 0
)