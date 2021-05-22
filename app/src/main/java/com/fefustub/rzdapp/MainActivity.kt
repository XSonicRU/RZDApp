package com.fefustub.rzdapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.fefustub.rzdapp.common.OnFragmentFinish


class MainActivity : AppCompatActivity(), OnFragmentFinish {
    private val REQUEST_ID_READ_PERMISSION = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askReadFilePermission()
        IncidentController.parse()
    }

    private fun askReadFilePermission() {
        val canRead = askPermission(
            REQUEST_ID_READ_PERMISSION,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Manifest.permission.MANAGE_EXTERNAL_STORAGE else Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (!canRead) {
            return
        }
    }

    private fun askPermission(requestId: Int, permissionName: String): Boolean {
        val permission = ActivityCompat.checkSelfPermission(this, permissionName)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(permissionName),
                requestId
            )
            return false
        }
        return true
    }

    override fun onFinish(status: Int?, data: Any?) {
        TODO("Not yet implemented")
    }
}