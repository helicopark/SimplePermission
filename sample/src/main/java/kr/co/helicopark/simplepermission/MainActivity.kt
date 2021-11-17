package kr.co.helicopark.simplepermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.helicopark.permission.SimplePermission
import kr.co.helicopark.permission.listener.OnPermissionListener
import kr.co.helicopark.simplepermission.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTest.setOnClickListener {
            SimplePermission
                .permissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {

                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val stringBuilder = StringBuilder()

                        for (permission in permissions) {
                            stringBuilder.append(permission).append(" ")
                        }

                        Toast.makeText(this@MainActivity, stringBuilder, Toast.LENGTH_SHORT).show()
                    }
                })
                .start(this)
        }
    }
}