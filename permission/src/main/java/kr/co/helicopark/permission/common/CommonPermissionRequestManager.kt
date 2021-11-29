package kr.co.helicopark.permission.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kr.co.helicopark.permission.PERMISSION_DENIED
import kr.co.helicopark.permission.PERMISSION_GRANTED
import kr.co.helicopark.permission.COMMON_PERMISSION_BROADCAST_RECEIVER
import kr.co.helicopark.permission.permission.PermissionRequestManager
import kr.co.helicopark.permission.permission.listener.OnPermissionListener


class CommonPermissionRequestManager(@NonNull private val permissions: Array<out String>) : PermissionRequestManager() {
    override var onPermissionListener: OnPermissionListener? = null

    private val permissionResultBroadcastReceiver: PermissionResultBroadcastReceiver by lazy {
        PermissionResultBroadcastReceiver()
    }

    override fun onPermissionListener(onPermissionListener: OnPermissionListener): PermissionRequestManager {
        this.onPermissionListener = onPermissionListener
        return this@CommonPermissionRequestManager
    }

    override fun start(context: Context) {
        LocalBroadcastManager.getInstance(context).registerReceiver(permissionResultBroadcastReceiver, IntentFilter(COMMON_PERMISSION_BROADCAST_RECEIVER))

        Intent(context, CommonPermissionActivity::class.java).let {
            it.putExtra("permissions", permissions)
            it.putExtra("broadcast", COMMON_PERMISSION_BROADCAST_RECEIVER)
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
        }
    }

    private inner class PermissionResultBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                COMMON_PERMISSION_BROADCAST_RECEIVER -> {
                    if (intent.hasExtra(PERMISSION_GRANTED)) {
                        onPermissionListener?.onPermissionGranted(intent.getStringArrayListExtra(PERMISSION_GRANTED)!!.toTypedArray())
                    }

                    if (intent.hasExtra(PERMISSION_DENIED)) {
                        onPermissionListener?.onPermissionDenied(intent.getStringArrayListExtra(PERMISSION_DENIED)!!.toTypedArray())
                    }

                    LocalBroadcastManager.getInstance(context).unregisterReceiver(permissionResultBroadcastReceiver)
                }
            }
        }
    }
}