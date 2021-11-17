package kr.co.helicopark.permission

import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import kotlin.collections.ArrayList

class SimplePermission{
    companion object {
        @JvmStatic
        @CheckResult(suggest = "requestCode()")
        fun permissions(@NonNull vararg permissions: String): PermissionRequestManager {
            return PermissionRequestManager(permissions)
        }

        @JvmStatic
        @CheckResult(suggest = "requestCode()")
        fun permissions(@NonNull permissions: ArrayList<String>): PermissionRequestManager {
            return PermissionRequestManager(permissions.toTypedArray())
        }

        @JvmStatic
        @CheckResult(suggest = "requestCode()")
        fun permissions(@NonNull permissions: List<String>): PermissionRequestManager {
            return PermissionRequestManager(permissions.toTypedArray())
        }
    }
}