package kr.co.helicopark.permission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.NonNull
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kr.co.helicopark.permission.listener.OnPermissionListener


class PermissionRequestManager(@NonNull private val permissions: Array<out String>) {
    private var onPermissionListener: OnPermissionListener? = null

    private val permissionResultBroadcastReceiver: PermissionResultBroadcastReceiver by lazy {
        PermissionResultBroadcastReceiver()
    }

    fun onPermissionListener(onPermissionListener: OnPermissionListener): PermissionRequestManager {
        this.onPermissionListener = onPermissionListener
        return this@PermissionRequestManager
    }

    fun start(context: Context) {
        LocalBroadcastManager.getInstance(context).registerReceiver(permissionResultBroadcastReceiver, IntentFilter(PERMISSION_RESULT_BROADCAST))

        Intent(context, PermissionActivity::class.java).let {
            it.putExtra("permissions", permissions)
            context.startActivity(it)
        }
    }

    private inner class PermissionResultBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                PERMISSION_RESULT_BROADCAST -> {
                    if (intent.hasExtra(PERMISSION_GRANTED_RESULT)) {
                        onPermissionListener?.onPermissionGranted(intent.getStringArrayListExtra(PERMISSION_GRANTED_RESULT)!!.toTypedArray())
                    }

                    if (intent.hasExtra(PERMISSION_DENIED_RESULT)) {
                        onPermissionListener?.onPermissionDenied(intent.getStringArrayListExtra(PERMISSION_DENIED_RESULT)!!.toTypedArray())
                    }
                }

                FINISH_PERMISSION_RESULT_BROADCAST -> {
                    context.unregisterReceiver(permissionResultBroadcastReceiver)
                }
            }
        }
    }
}