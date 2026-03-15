package com.example.kotlinhotel.presentation.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinhotel.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PaymentViewModel by viewModels()
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = args.service
        val fmt = NumberFormat.getNumberInstance(Locale("ru"))
        binding.tvServiceName.text = service.title
        binding.tvServiceDescription.text = service.description
        binding.tvTotal.text = "Total: ${fmt.format(service.price.toInt())} ₽"

        binding.btnPay.setOnClickListener {
            viewModel.startPayment(service)
        }
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is PaymentState.Processing -> {
                            binding.progressPayment.visibility = View.VISIBLE
                            binding.tvProcessing.visibility = View.VISIBLE
                            binding.btnPay.visibility = View.GONE
                        }
                        is PaymentState.Success -> {
                            binding.progressPayment.visibility = View.GONE
                            binding.tvProcessing.visibility = View.GONE
                            Snackbar.make(binding.root, "Payment successful!", Snackbar.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        else -> {
                            binding.progressPayment.visibility = View.GONE
                            binding.tvProcessing.visibility = View.GONE
                            binding.btnPay.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
