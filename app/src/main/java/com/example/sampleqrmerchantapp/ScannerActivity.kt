package com.example.sampleqrmerchantapp

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleqrmerchantapp.databinding.ActivityScannerBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory

class ScannerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScannerBinding
    private val deepLinkSchema = "upi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formats = listOf(BarcodeFormat.QR_CODE)
        binding.barcodeScanner.decoderFactory = DefaultDecoderFactory(formats)

        startCamera()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.retryMessage.visibility = View.VISIBLE
        }, 10000)
    }

    private fun startCamera() {
        binding.barcodeScanner.initializeFromIntent(intent)
        binding.barcodeScanner.decodeContinuous(callback)
    }

    private val callback = BarcodeCallback { result ->
        if (result != null) {
            val deeplink = result.text
            val schema = deeplink.split("://")[0];

            // QR Code result for upi://pay is fetched
            if(schema.equals(deepLinkSchema, true)) {
                intent.putExtra("QR_DATA", deeplink)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }
}