package com.example.sampleqrmerchantapp.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sampleqrmerchantapp.R
import com.example.sampleqrmerchantapp.databinding.FragmentSubmitBinding
import com.example.sampleqrmerchantapp.viewmodel.TransactionViewModel

class SubmitFragment : Fragment() {
    private lateinit var binding: FragmentSubmitBinding
    private lateinit var sharedViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSubmitBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        sharedViewModel.parseDeepLink()
        setSubmitScreenData()
        binding.payButton.setOnClickListener {
            onProceedClick()
        }
        return binding.root
    }

    private fun onProceedClick() {
        if(!isValidationError()) {
            sharedViewModel.mid = binding.etMid.text.toString()
            sharedViewModel.amount = binding.etAmount.text.toString()
            sharedViewModel.proceedTransaction()
            findNavController().navigate(R.id.action_submitFragment_to_transactionSummaryFragment)
        }
    }

    private fun isValidationError() : Boolean {
        when {
            binding.etMid.text.isEmpty() -> {
                binding.etMid.requestFocus()
                Toast.makeText(requireActivity(), "MID cannot be empty", Toast.LENGTH_SHORT).show()
                return true;
            }
            binding.etAmount.text.isEmpty() -> {
                binding.etAmount.requestFocus()
                Toast.makeText(requireActivity(), "Please enter valid amount", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }

    private fun setSubmitScreenData() {
        binding.merchantName.text = sharedViewModel.merchantName
        if(sharedViewModel.isDynamic) {
            binding.etAmount.text = Editable.Factory.getInstance().newEditable(sharedViewModel.amount)
            binding.etAmount.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}