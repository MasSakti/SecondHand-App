package id.co.binar.secondhand.util

import android.app.Activity
import com.vmadalin.easypermissions.EasyPermissions

const val PERMISSION_CAMERA_AND_WRITE_EXTERNAL = 111

fun requestCameraAndWriteExternalPermission(host: Activity) {
    EasyPermissions.requestPermissions(
        host,
        "Fitur ini tidak akan bisa jalan tanpa adanya izin kamera!",
        PERMISSION_CAMERA_AND_WRITE_EXTERNAL,
        perms = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    )
}