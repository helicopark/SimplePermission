package kr.co.helicopark.simplepermission

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kr.co.helicopark.permission.READ_EXTERNAL_STORAGE
import kr.co.helicopark.permission.SimplePermission
import kr.co.helicopark.permission.WRITE_EXTERNAL_STORAGE
import kr.co.helicopark.permission.permission.listener.OnPermissionListener
import kr.co.helicopark.simplepermission.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCommon.setOnClickListener {
            SimplePermission.permissions(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnLocation.setOnClickListener {
            SimplePermission.locationPermission().onPermissionListener(object : OnPermissionListener {
                override fun onPermissionGranted(permissions: Array<out String>) {
                    val permissionStringBuilder = StringBuilder()
                    for (permission in permissions) {
                        permissionStringBuilder.append(permission).append("\n")
                    }
                    Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                }

                override fun onPermissionDenied(permissions: Array<out String>) {
                    val permissionStringBuilder = StringBuilder()
                    for (permission in permissions) {
                        permissionStringBuilder.append(permission).append("\n")
                    }

                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("거부")
                        setMessage(permissionStringBuilder)
                        setPositiveButton("확인", null)
                        show()
                    }
                }

            }).start(this@MainActivity)
        }

        binding.btnBackgroundLocationBasicString.setOnClickListener {
            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .basicBackgroundLocationDescriptionDialog("알림", "백그라운드 알림입니다.")
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }

                }).start(this@MainActivity)
        }

        binding.btnBackgroundLocationBasicRes.setOnClickListener {
            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .basicBackgroundLocationDescriptionDialog(
                    R.string.background_location_description_dialog_title,
                    R.string.background_location_description_dialog_message
                )
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        val alertDialogx = AlertDialog.Builder(this)
        val alertDialog = AlertDialog.Builder(this)
        val dialog = Dialog(this@MainActivity)

        binding.btnBackgroundLocationCustomDialog.setOnClickListener {
            val dialogView = View.inflate(this, R.layout.dialog_background_location, null)
            dialog.setContentView(dialogView)

            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .customBackgroundLocationDescriptionDialog(dialog, dialogView.findViewById(R.id.tv_ok))
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnBackgroundLocationCustom.setOnClickListener {
            val alertDialogView = View.inflate(this, R.layout.dialog_background_location, null)
            alertDialog.setView(alertDialogView)

            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .customBackgroundLocationDescriptionDialog(alertDialog, alertDialogView.findViewById(R.id.tv_ok))
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnBackgroundLocationCustomX.setOnClickListener {
            val alertDialogxView = View.inflate(this, R.layout.dialog_background_location, null)
            alertDialogx.setView(alertDialogxView)

            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .customBackgroundLocationDescriptionDialog(alertDialogx, alertDialogxView.findViewById(R.id.tv_ok))
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnBackgroundLocationCustomCreate.setOnClickListener {
            val alertDialogView = View.inflate(this, R.layout.dialog_background_location, null)
            alertDialog.setView(alertDialogView)

            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .customBackgroundLocationDescriptionDialog(alertDialog.create(), alertDialogView.findViewById(R.id.tv_ok))
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }

        binding.btnBackgroundLocationCustomXCreate.setOnClickListener {
            val alertDialogxView = View.inflate(this, R.layout.dialog_background_location, null)
            alertDialogx.setView(alertDialogxView)

            SimplePermission
                .locationPermission()
                .backgroundLocationPermission()
                .customBackgroundLocationDescriptionDialog(alertDialogx.create(), alertDialogxView.findViewById(R.id.tv_ok))
                .onPermissionListener(object : OnPermissionListener {
                    override fun onPermissionGranted(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }
                        Toast.makeText(this@MainActivity, "허용\n$permissionStringBuilder", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(permissions: Array<out String>) {
                        val permissionStringBuilder = StringBuilder()
                        for (permission in permissions) {
                            permissionStringBuilder.append(permission).append("\n")
                        }

                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("거부")
                            setMessage(permissionStringBuilder)
                            setPositiveButton("확인", null)
                            show()
                        }
                    }
                })
                .start(this@MainActivity)
        }
    }
}