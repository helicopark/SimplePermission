package kr.co.helicopark.permission.listener

import androidx.annotation.NonNull

interface OnPermissionListener {
    fun onPermissionGranted(@NonNull permissions: Array<out String>)
    fun onPermissionDenied(@NonNull permissions: Array<out String>)
}