package kr.co.helicopark.permission.location.background

import android.app.AlertDialog
import android.app.Dialog
import android.view.View
import androidx.annotation.StringRes
import kr.co.helicopark.permission.location.LocationPermissionRequestManager

interface BackgroundLocationDescriptionDialogType {
    fun customBackgroundLocationDescriptionDialog(customDialog: AlertDialog, positiveButton: View): LocationPermissionRequestManager
    fun customBackgroundLocationDescriptionDialog(customDialog: AlertDialog.Builder, positiveButton: View): LocationPermissionRequestManager
    fun customBackgroundLocationDescriptionDialog(customDialog: androidx.appcompat.app.AlertDialog, positiveButton: View): LocationPermissionRequestManager
    fun customBackgroundLocationDescriptionDialog(customDialog: androidx.appcompat.app.AlertDialog.Builder, positiveButton: View): LocationPermissionRequestManager
    fun customBackgroundLocationDescriptionDialog(customDialog: Dialog, positiveButton: View): LocationPermissionRequestManager

    fun basicBackgroundLocationDescriptionDialog(title: String, message: String): LocationPermissionRequestManager
    fun basicBackgroundLocationDescriptionDialog(@StringRes title: Int, @StringRes message: Int): LocationPermissionRequestManager

    fun noNeedBackgroundLocationDescriptionDialog(): LocationPermissionRequestManager
}