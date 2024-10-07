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
import androidx.lifecycle.ViewModelProvider
import com.example.sampleqrmerchantapp.databinding.ActivityMainBinding
import com.example.sampleqrmerchantapp.repository.Repository
import com.example.sampleqrmerchantapp.viewmodel.TransactionViewModel
import com.example.sampleqrmerchantapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var sharedViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository: Repository = (application as SampleQRMerchantApplication).repository
        sharedViewModel = ViewModelProvider(this, ViewModelFactory(repository))[TransactionViewModel::class.java]
    }

}