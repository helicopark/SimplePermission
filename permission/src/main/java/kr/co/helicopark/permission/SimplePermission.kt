package kr.co.helicopark.permission

import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import kr.co.helicopark.permission.location.LocationPermissionRequestManager
import kr.co.helicopark.permission.common.CommonPermissionRequestManager
import kotlin.collections.ArrayList

class SimplePermission {
    companion object {
        @JvmStatic
        @CheckResult(suggest = "onPermissionListener()")
        fun permissions(@NonNull vararg permissions: String): CommonPermissionRequestManager {
            return CommonPermissionRequestManager(permissions)
        }

        @JvmStatic
        @CheckResult(suggest = "onPermissionListener()")
        fun permissions(@NonNull permissions: ArrayList<String>): CommonPermissionRequestManager {
            return CommonPermissionRequestManager(permissions.toTypedArray())
        }

        @JvmStatic
        @CheckResult(suggest = "onPermissionListener()")
        fun permissions(@NonNull permissions: List<String>): CommonPermissionRequestManager {
            return CommonPermissionRequestManager(permissions.toTypedArray())
        }

        @JvmStatic
        @CheckResult(suggest = "onPermissionListener()")
        fun locationPermission(): LocationPermissionRequestManager {
            return LocationPermissionRequestManager()
        }
    }
}