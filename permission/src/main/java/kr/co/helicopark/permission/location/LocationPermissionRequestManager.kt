package kr.co.helicopark.permission.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.CheckResult
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kr.co.helicopark.permission.*
import kr.co.helicopark.permission.PERMISSION_DENIED
import kr.co.helicopark.permission.PERMISSION_GRANTED
import kr.co.helicopark.permission.COMMON_PERMISSION_BROADCAST_RECEIVER
import kr.co.helicopark.permission.permission.listener.OnPermissionListener
import kr.co.helicopark.permission.permission.PermissionRequestManager

class LocationPermissionRequestManager : PermissionRequestManager() {
    override var onPermissionListener: OnPermissionListener? = null
    private var hasBackgroundPermission: Boolean = false

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
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(locationPermissionResultBroadcastReceiver, IntentFilter(COMMON_PERMISSION_BROADCAST_RECEIVER))

        Intent(context, LocationPermissionActivity::class.java).let {
            it.putExtra("broadcast", COMMON_PERMISSION_BROADCAST_RECEIVER)
            it.putExtra("permissions", arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    @CheckResult(suggest = "start()")
    fun backgroundPermission(hasBackgroundPermission: Boolean): LocationPermissionRequestManager {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.hasBackgroundPermission = hasBackgroundPermission
        } else {
            this.hasBackgroundPermission = false
        }

        return this@LocationPermissionRequestManager
    }

    private fun requestBackgroundPermission(context: Context) {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(backgroundLocationPermissionResultBroadcastReceiver, IntentFilter(BACKGROUND_LOCATION_PERMISSION_BROADCAST_RECEIVER))

        Intent(context, LocationPermissionActivity::class.java).let {
            it.putExtra("broadcast", BACKGROUND_LOCATION_PERMISSION_BROADCAST_RECEIVER)
            it.putExtra("permissions", arrayOf(ACCESS_BACKGROUND_LOCATION))
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
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
                            if (permissions[0].equals(ACCESS_FINE_LOCATION) or permissions[0].equals(ACCESS_COARSE_LOCATION)) {
                                requestBackgroundPermission(context)
                            } else {
                                onPermissionListener?.onPermissionGranted(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION))
                            }
                        }
                    }

                    if (intent.hasExtra(PERMISSION_DENIED)) {
                        val permissions = intent.getStringArrayListExtra(PERMISSION_DENIED)!!.toTypedArray()

                        if (!hasBackgroundPermission) {
                            onPermissionListener?.onPermissionDenied(permissions)
                        } else {
                            onPermissionListener?.onPermissionDenied(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, ACCESS_BACKGROUND_LOCATION))
                        }
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