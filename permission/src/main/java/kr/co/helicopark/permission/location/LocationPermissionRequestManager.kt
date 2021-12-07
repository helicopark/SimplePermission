package kr.co.helicopark.permission.location

import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.view.View
import androidx.annotation.CheckResult
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kr.co.helicopark.permission.*
import kr.co.helicopark.permission.PERMISSION_DENIED
import kr.co.helicopark.permission.PERMISSION_GRANTED
import kr.co.helicopark.permission.COMMON_PERMISSION_BROADCAST_RECEIVER
import kr.co.helicopark.permission.location.background.BackgroundLocationDescriptionDialogRequestManager
import kr.co.helicopark.permission.permission.listener.OnPermissionListener
import kr.co.helicopark.permission.permission.PermissionRequestManager

class LocationPermissionRequestManager : PermissionRequestManager() {
    override var onPermissionListener: OnPermissionListener? = null
    private var hasBackgroundPermission: Boolean = false

    private var backgroundLocationDescriptionDialog: AlertDialog.Builder? = null

    internal var backgroundLocationDescriptionDialogTitle: String? = null
    internal var backgroundLocationDescriptionDialogMessage: String? = null
    internal var backgroundLocationDescriptionDialogStringResTitle: Int? = null
    internal var backgroundLocationDescriptionDialogStringResMessage: Int? = null

    internal var customBackgroundLocationDescriptionDialog: Dialog? = null
    internal var customBackgroundLocationDescriptionDialogPositiveButton: View? = null

    private var context: Context? = null

    private val locationPermissionResultBroadcastReceiver: LocationPermissionResultBroadcastReceiver by lazy {
        LocationPermissionResultBroadcastReceiver()
    }

    private val backgroundLocationPermissionResultBroadcastReceiver: BackgroundLocationPermissionResultBroadcastReceiver by lazy {
        BackgroundLocationPermissionResultBroadcastReceiver()
    }

    override fun onPermissionListener(onPermissionListener: OnPermissionListener): PermissionRequestManager {
        this.onPermissionListener = onPermissionListener
        return this@LocationPermissionRequestManager
    }

    override fun start(context: Context) {
        this.context = context

        LocalBroadcastManager.getInstance(context)
            .registerReceiver(locationPermissionResultBroadcastReceiver, IntentFilter(COMMON_PERMISSION_BROADCAST_RECEIVER))

        val permissions = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
        if (hasBackgroundPermission && Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            permissions.plus(ACCESS_BACKGROUND_LOCATION)
        }

        Intent(context, LocationPermissionActivity::class.java).let {
            it.putExtra("broadcast", COMMON_PERMISSION_BROADCAST_RECEIVER)
            it.putExtra("permissions", permissions)
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    private fun showBackgroundLocationDescriptionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (customBackgroundLocationDescriptionDialog != null) {
                customBackgroundLocationDescriptionDialogPositiveButton!!.setOnClickListener {
                    requestBackgroundLocationPermission(context!!)
                }

                customBackgroundLocationDescriptionDialog!!.show()
                return
            }

            if (!TextUtils.isEmpty(backgroundLocationDescriptionDialogTitle) && !TextUtils.isEmpty(backgroundLocationDescriptionDialogMessage)) {
                backgroundLocationDescriptionDialog = AlertDialog.Builder(context)
                backgroundLocationDescriptionDialog!!.setTitle(backgroundLocationDescriptionDialogTitle)
                backgroundLocationDescriptionDialog!!.setMessage(backgroundLocationDescriptionDialogMessage)
                backgroundLocationDescriptionDialog!!.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        requestBackgroundLocationPermission(context!!)
                    }
                })

                backgroundLocationDescriptionDialog!!.setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
                        onPermissionListener?.onPermissionDenied(arrayOf(ACCESS_BACKGROUND_LOCATION))
                    }
                })

                backgroundLocationDescriptionDialog!!.show()
                return
            }

            if (backgroundLocationDescriptionDialogStringResTitle != null && backgroundLocationDescriptionDialogStringResMessage != null) {
                backgroundLocationDescriptionDialog = AlertDialog.Builder(context)
                backgroundLocationDescriptionDialog!!.setTitle(backgroundLocationDescriptionDialogStringResTitle!!)
                backgroundLocationDescriptionDialog!!.setMessage(backgroundLocationDescriptionDialogStringResMessage!!)
                backgroundLocationDescriptionDialog!!.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        requestBackgroundLocationPermission(context!!)
                    }
                })

                backgroundLocationDescriptionDialog!!.setNegativeButton(android.R.string.cancel, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
                        onPermissionListener?.onPermissionDenied(arrayOf(ACCESS_BACKGROUND_LOCATION))
                    }
                })

                backgroundLocationDescriptionDialog!!.show()
                return
            }

            onPermissionListener?.onPermissionDenied(arrayOf(ACCESS_BACKGROUND_LOCATION))
            throw IllegalArgumentException("if your api is 30 or upper and you want use access background location, you need to show dialog explaining why you are using it")
        }
    }

    private fun requestBackgroundLocationPermission(context: Context) {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(backgroundLocationPermissionResultBroadcastReceiver, IntentFilter(BACKGROUND_LOCATION_PERMISSION_BROADCAST_RECEIVER))

        Intent(context, LocationPermissionActivity::class.java).let {
            it.putExtra("broadcast", BACKGROUND_LOCATION_PERMISSION_BROADCAST_RECEIVER)
            it.putExtra("permissions", arrayOf(ACCESS_BACKGROUND_LOCATION))
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    @CheckResult(suggest = "BackgroundLocationDescriptionDialog()")
    fun backgroundLocationPermission(): BackgroundLocationDescriptionDialogRequestManager {
        this.hasBackgroundPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

        return BackgroundLocationDescriptionDialogRequestManager(this)
    }

    private inner class LocationPermissionResultBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                COMMON_PERMISSION_BROADCAST_RECEIVER -> {
                    if (intent.hasExtra(PERMISSION_GRANTED)) {
                        val permissions = intent.getStringArrayListExtra(PERMISSION_GRANTED)!!.toTypedArray()

                        if (!hasBackgroundPermission) {
                            onPermissionListener?.onPermissionGranted(permissions)
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                if (context.checkSelfPermission(ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION))
                                } else {
                                    showBackgroundLocationDescriptionDialog()
                                }
                            } else {
                                onPermissionListener?.onPermissionGranted(permissions)
                            }
                        }
                    }

                    if (intent.hasExtra(PERMISSION_DENIED)) {
                        val permissions = intent.getStringArrayListExtra(PERMISSION_DENIED)!!.toTypedArray()
                        onPermissionListener?.onPermissionDenied(permissions)
                    }

                    LocalBroadcastManager.getInstance(context).unregisterReceiver(locationPermissionResultBroadcastReceiver)
                }
            }
        }
    }

    private inner class BackgroundLocationPermissionResultBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BACKGROUND_LOCATION_PERMISSION_BROADCAST_RECEIVER -> {
                    if (intent.hasExtra(PERMISSION_GRANTED)) {
                        onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION))
                    }

                    if (intent.hasExtra(PERMISSION_DENIED)) {
                        onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
                        onPermissionListener?.onPermissionDenied(arrayOf(ACCESS_BACKGROUND_LOCATION))
                    }

                    LocalBroadcastManager.getInstance(context).unregisterReceiver(backgroundLocationPermissionResultBroadcastReceiver)
                }
            }
        }
    }
}