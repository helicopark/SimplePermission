package kr.co.helicopark.permission.permission

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

internal abstract class PermissionActivity : AppCompatActivity() {
    internal abstract var broadcast: String
    internal abstract var permissions : Array<out String>

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}