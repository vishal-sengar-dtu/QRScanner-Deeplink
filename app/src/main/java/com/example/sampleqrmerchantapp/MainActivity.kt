package com.example.sampleqrmerchantapp

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.sampleqrmerchantapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //Camera Runtime Permission Handling Launcher
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted : Boolean ->
        if(isGranted) {
            startCamera()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)) {
                enablePermissionFromSettings()
            } else {
                checkAndRequestCameraPermission()
            }
        }
    }

    //Scanner Activity Result Launcher
    private val scanResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            val deeplink = result.data?.getStringExtra("QR_DATA")
            deeplink?.let {
                Toast.makeText(this, "QR Data: $it", Toast.LENGTH_LONG).show()
                val submitIntent = Intent(this, SubmitActivity::class.java)
                submitIntent.putExtra("DEEPLINK", deeplink)
                startActivity(submitIntent)
            }
        } else {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanButton.setOnClickListener {
            checkAndRequestCameraPermission()
        }
    }

    private fun checkAndRequestCameraPermission() {
        if(ActivityCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)) {
                showPermissionRationale()
            } else {
                requestCameraPermissionLauncher.launch(CAMERA)
            }
        }
    }

    private fun startCamera() {
        val scannerIntent = Intent(this, ScannerActivity::class.java)
        scanResultLauncher.launch(scannerIntent)
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Camera Permission Needed")
            .setMessage("Camera access is required to scan QR code. Please grant the permission")
            .setPositiveButton("Allow") { _,_ -> requestCameraPermissionLauncher.launch(CAMERA)}
            .setNegativeButton("Deny", null)
            .create()
            .show()
    }

    private fun enablePermissionFromSettings() {
        AlertDialog.Builder(this)
            .setTitle("Camera Permission Denied")
            .setMessage("Camera access is required to scan QR code. You can enable it from settings.")
            .setPositiveButton("Go to Settings") { _,_ ->
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

}