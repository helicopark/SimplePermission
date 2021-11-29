package kr.co.helicopark.simplepermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kr.co.helicopark.permission.SimplePermission
import kr.co.helicopark.permission.permission.listener.OnPermissionListener
import kr.co.helicopark.simplepermission.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackgroundLocation.setOnClickListener {
            SimplePermission
                .locationPermission()
                .backgroundPermission(true)
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val stringBuilder = StringBuilder()
                        for (permission in permissions) {
                            stringBuilder.append(permission).append(" ")
                        }
                        Toast.makeText(this@MainActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val stringBuilder = StringBuilder()
                        for (permission in permissions) {
                            stringBuilder.append(permission).append(" ")
                        }
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("test")
                        builder.setMessage(stringBuilder.toString())
                        builder.setCancelable(false)

                        builder.setPositiveButton("확인", null)
                        builder.show()

                    }
                })
                .start(this@MainActivity)
        }

        binding.btnLocation.setOnClickListener{
            SimplePermission
                .locationPermission()
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val stringBuilder = StringBuilder()
                        for (permission in permissions) {
                            stringBuilder.append(permission).append(" ")
                        }
                        Toast.makeText(this@MainActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val stringBuilder = StringBuilder()
                        for (permission in permissions) {
                            stringBuilder.append(permission).append(" ")
                        }
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("test")
                        builder.setMessage(stringBuilder.toString())
                        builder.setCancelable(false)

                        builder.setPositiveButton("확인", null)
                        builder.show()
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnTest.setOnClickListener{
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