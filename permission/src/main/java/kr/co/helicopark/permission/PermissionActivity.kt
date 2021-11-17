package kr.co.helicopark.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.collections.ArrayList

class PermissionActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        sendBroadcast(Intent(FINISH_PERMISSION_RESULT_BROADCAST))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(intent.extras!!.get("permissions") as Array<out String>, 1000)
        } else {
            LocalBroadcastManager.getInstance(this@PermissionActivity).sendBroadcast(
                Intent(PERMISSION_RESULT_BROADCAST).putExtra(PERMISSION_GRANTED_RESULT, intent.extras!!.get("permissions") as Array<out String>)
            )
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val grantedPermissionList = ArrayList<String>()
        val deniedPermissionList = ArrayList<String>()

        val permissionResultIntent = Intent(PERMISSION_RESULT_BROADCAST)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissionList.add(permission)
                } else {
                    deniedPermissionList.add(permission)
                }
            }
        } else {
            grantedPermissionList.addAll(permissions)
        }

        if (grantedPermissionList.isNotEmpty()) {
            permissionResultIntent.putExtra(PERMISSION_GRANTED_RESULT, grantedPermissionList)
        }

        if (deniedPermissionList.isNotEmpty()) {
            permissionResultIntent.putExtra(PERMISSION_DENIED_RESULT, deniedPermissionList)
        }

        LocalBroadcastManager.getInstance(this@PermissionActivity).sendBroadcast(permissionResultIntent)

        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}