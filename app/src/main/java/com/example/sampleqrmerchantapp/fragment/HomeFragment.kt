package com.example.sampleqrmerchantapp.fragment

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sampleqrmerchantapp.R
import com.example.sampleqrmerchantapp.ScannerActivity
import com.example.sampleqrmerchantapp.databinding.FragmentHomeBinding
import com.example.sampleqrmerchantapp.viewmodel.TransactionViewModel

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var sharedViewModel : TransactionViewModel

    //Camera Runtime Permission Handling Launcher
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted : Boolean ->
        if(isGranted) {
            startCamera()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)) {
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
                Toast.makeText(requireContext(), "QR Data: $it", Toast.LENGTH_LONG).show()
                sharedViewModel.deepLink = Uri.parse(it)
                findNavController().navigate(R.id.action_homeFragment_to_submitFragment)
            }!!
        } else {
            Toast.makeText(requireContext(), "Scan cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]

        binding.scanButton.setOnClickListener {
            checkAndRequestCameraPermission()
        }

        return binding.root
    }

    private fun checkAndRequestCameraPermission() {
        if(ActivityCompat.checkSelfPermission(requireContext(), CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)) {
                showPermissionRationale()
            } else {
                requestCameraPermissionLauncher.launch(CAMERA)
            }
        }
    }

    private fun startCamera() {
        val scannerIntent = Intent(requireActivity(), ScannerActivity::class.java)
        scanResultLauncher.launch(scannerIntent)
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle("Camera Permission Needed")
            .setMessage("Camera access is required to scan QR code. Please grant the permission")
            .setPositiveButton("Allow") { _,_ -> requestCameraPermissionLauncher.launch(CAMERA)}
            .setNegativeButton("Deny", null)
            .create()
            .show()
    }

    private fun enablePermissionFromSettings() {
        AlertDialog.Builder(requireContext())
            .setTitle("Camera Permission Denied")
            .setMessage("Camera access is required to scan QR code. You can enable it from settings.")
            .setPositiveButton("Go to Settings") { _,_ ->
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", requireActivity().packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

}