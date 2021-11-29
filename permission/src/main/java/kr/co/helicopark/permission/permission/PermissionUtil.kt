package kr.co.helicopark.permission.permission

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isMarshMallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M