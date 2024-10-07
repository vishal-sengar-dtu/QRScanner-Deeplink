package com.example.sampleqrmerchantapp.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sampleqrmerchantapp.R
import com.example.sampleqrmerchantapp.databinding.AlertDialogBoxBinding
import com.example.sampleqrmerchantapp.databinding.FragmentTransactionSummaryBinding
import com.example.sampleqrmerchantapp.model.PaymentResponse
import com.example.sampleqrmerchantapp.network.NetworkResult
import com.example.sampleqrmerchantapp.viewmodel.TransactionViewModel

class TransactionSummaryFragment : Fragment() {
    private lateinit var binding : FragmentTransactionSummaryBinding
    private lateinit var sharedViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransactionSummaryBinding.inflate(layoutInflater)
        sharedViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        observeTransactionStatus()
        return binding.root
    }

    private fun observeTransactionStatus() {
        sharedViewModel.txnResponse.observe(viewLifecycleOwner) { status ->
            when(status) {
                is NetworkResult.Success -> {
                    binding.loader.visibility = View.GONE
                    status.data?.let { showTransactionStatus(it) }
                }
                is NetworkResult.Error -> {
                    binding.loader.visibility = View.GONE
                    showCustomDialog("Network Error", status.errorMessage.toString(), "Rescan")
                }
                is NetworkResult.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showTransactionStatus(data: PaymentResponse) {
        binding.statusCard.visibility = View.VISIBLE
        binding.merchantName.text = data.businessName
        binding.message.text = data.message
        binding.txnAmount.text = "₹ ${data.amount}"
        binding.txnDate.text = data.txnDate
        binding.txnOrderId.text = data.orderId
        when(data.status) {
            "TXN_SUCCESS" -> {
                binding.doneButton.visibility = View.VISIBLE
                binding.ivStatus.setImageResource(R.drawable.success_tick)
                binding.txnStatus.text = "PAYMENT SUCCESSFUL"
            }
            "TXN_FAILURE" -> {
                binding.rescanButton.visibility = View.VISIBLE
                binding.ivStatus.setImageResource(R.drawable.failed_icon)
                binding.txnStatus.text = "PAYMENT FAILED"
            }
        }
    }

    private fun showCustomDialog(title: String, message: String, btnText: String) {
        val dialogBinding = AlertDialogBoxBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.alertCardBox)
            .setCancelable(false)
            .create()

        dialogBinding.title.text = title
        dialogBinding.alertMessage.text = message
        dialogBinding.positiveButton.text = btnText

        dialogBinding.positiveButton.setOnClickListener {
            alertDialog.dismiss()
            findNavController().popBackStack()
        }

        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}