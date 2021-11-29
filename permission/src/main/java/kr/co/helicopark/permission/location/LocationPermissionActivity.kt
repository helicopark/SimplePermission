package kr.co.helicopark.permission.location

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kr.co.helicopark.permission.PERMISSION_DENIED
import kr.co.helicopark.permission.PERMISSION_GRANTED
import kr.co.helicopark.permission.permission.PermissionActivity
import kr.co.helicopark.permission.permission.isMarshMallow
import kotlin.collections.ArrayList

internal class LocationPermissionActivity : PermissionActivity() {
    override lateinit var broadcast: String
    override lateinit var permissions: Array<out String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        broadcast = intent.extras!!.getString("broadcast")!!
        permissions = intent.getStringArrayExtra("permissions")!!

        if (isMarshMallow()) {
            requestPermissions(permissions, 1000)
        } else {
            LocalBroadcastManager.getInstance(this@LocationPermissionActivity).sendBroadcast(
                Intent(broadcast).putExtra(PERMISSION_GRANTED, permissions)
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val grantedPermissionList = ArrayList<String>()
        val deniedPermissionList = ArrayList<String>()

        val permissionResultIntent = Intent(broadcast)
        if (isMarshMallow()) {
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
            permissionResultIntent.putExtra(PERMISSION_GRANTED, grantedPermissionList)
        }

        if (deniedPermissionList.isNotEmpty()) {
            permissionResultIntent.putExtra(PERMISSION_DENIED, deniedPermissionList)
        }

        LocalBroadcastManager.getInstance(this@LocationPermissionActivity).sendBroadcast(permissionResultIntent)
        finish()
    }
}