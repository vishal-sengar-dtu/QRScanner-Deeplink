package com.example.sampleqrmerchantapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sampleqrmerchantapp.databinding.ActivitySubmitBinding
import com.example.sampleqrmerchantapp.viewmodel.SubmitViewModel

class SubmitActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubmitBinding
    private lateinit var viewModel: SubmitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubmitBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SubmitViewModel::class.java]
        setContentView(binding.root)
        viewModel.deepLink = Uri.parse(intent.getStringExtra("DEEPLINK")!!)
        viewModel.parseDeepLink()
        setSubmitScreenData()
        binding.payButton.setOnClickListener {
            onProceedClick()
        }
    }

    private fun onProceedClick() {
        errorValidation()
        viewModel.mid = binding.etMid.text.toString()
        viewModel.amount = binding.etAmount.text.toString()
        viewModel.getTxnStatus()
        val txnSummaryIntent = Intent(this, TransactionSummaryActivity::class.java)
        startActivity(txnSummaryIntent)
    }

    private fun errorValidation() {
        if(binding.etMid.text.isEmpty()) {
            binding.etMid.requestFocus()
            Toast.makeText(this, "MID cannot be empty", Toast.LENGTH_SHORT).show()
        }

        if(binding.etAmount.text.isEmpty()) {
            binding.etAmount.requestFocus()
            Toast.makeText(this, "Please enter valid amount", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSubmitScreenData() {
        binding.merchantName.text = viewModel.merchantName
        if(viewModel.isDynamic) {
            binding.etAmount.text = Editable.Factory.getInstance().newEditable(viewModel.amount)
            binding.etAmount.isEnabled = false
        }
    }

}