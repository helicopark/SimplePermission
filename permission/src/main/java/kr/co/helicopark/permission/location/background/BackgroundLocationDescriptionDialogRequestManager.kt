package kr.co.helicopark.permission.location.background

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AlertDialog
import kr.co.helicopark.permission.location.LocationPermissionRequestManager

class BackgroundLocationDescriptionDialogRequestManager(private val locationPermissionRequestManager: LocationPermissionRequestManager) :
    BackgroundLocationDescriptionDialogType {

    override fun customBackgroundLocationDescriptionDialog(customDialog: AlertDialog.Builder, positiveButton: View): LocationPermissionRequestManager {
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialog = customDialog.create()
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialogPositiveButton = positiveButton
        return locationPermissionRequestManager
    }

    override fun customBackgroundLocationDescriptionDialog(
        customDialog: android.app.AlertDialog.Builder,
        positiveButton: View
    ): LocationPermissionRequestManager {
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialog = customDialog.create()
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialogPositiveButton = positiveButton
        return locationPermissionRequestManager
    }

    override fun customBackgroundLocationDescriptionDialog(customDialog: android.app.AlertDialog, positiveButton: View): LocationPermissionRequestManager {
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialog = customDialog
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialogPositiveButton = positiveButton
        return locationPermissionRequestManager
    }

    override fun customBackgroundLocationDescriptionDialog(customDialog: AlertDialog, positiveButton: View): LocationPermissionRequestManager {
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialog = customDialog
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialogPositiveButton = positiveButton
        return locationPermissionRequestManager
    }

    override fun customBackgroundLocationDescriptionDialog(customDialog: Dialog, positiveButton: View): LocationPermissionRequestManager {
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialog = customDialog
        locationPermissionRequestManager.customBackgroundLocationDescriptionDialogPositiveButton = positiveButton
        return locationPermissionRequestManager
    }

    override fun basicBackgroundLocationDescriptionDialog(title: String, message: String): LocationPermissionRequestManager {
        locationPermissionRequestManager.backgroundLocationDescriptionDialogTitle = title
        locationPermissionRequestManager.backgroundLocationDescriptionDialogMessage = message
        return locationPermissionRequestManager
    }

    override fun basicBackgroundLocationDescriptionDialog(title: Int, message: Int): LocationPermissionRequestManager {
        locationPermissionRequestManager.backgroundLocationDescriptionDialogStringResTitle = title
        locationPermissionRequestManager.backgroundLocationDescriptionDialogStringResMessage = message
        return locationPermissionRequestManager
    }

    override fun noNeedBackgroundLocationDescriptionDialog(): LocationPermissionRequestManager {
        return locationPermissionRequestManager
    }
}