package kr.co.helicopark.permission.permission

import android.content.Context
import androidx.annotation.CheckResult
import kr.co.helicopark.permission.location.LocationPermissionRequestManager
import kr.co.helicopark.permission.common.CommonPermissionRequestManager
import kr.co.helicopark.permission.permission.listener.OnPermissionListener

abstract class PermissionRequestManager {
    internal abstract var onPermissionListener: OnPermissionListener?

    @CheckResult(suggest = "start()")
    abstract fun onPermissionListener(onPermissionListener: OnPermissionListener): PermissionRequestManager
    abstract fun start(context: Context)
}