package com.example.kotlinhotel.presentation.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentPaymentBinding
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.presentation.ViewModelFactory

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PaymentViewModel by viewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var service: HotelService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        service = arguments?.getParcelable(ARG_SERVICE)
            ?: HotelService(0, "Услуга", "", 0.0, com.example.kotlinhotel.domain.model.ServiceCategory.SPA)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.textServiceName.text = service.title
        binding.textAmount.text = "К оплате: ${service.price.toInt()} ₽"

        binding.btnPay.setOnClickListener { viewModel.processPayment() }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PaymentUiState.Idle -> {
                    binding.btnPay.isEnabled = true
                    binding.progressPayment.isVisible = false
                    binding.textProcessing.isVisible = false
                }
                PaymentUiState.Processing -> {
                    binding.btnPay.isEnabled = false
                    binding.progressPayment.isVisible = true
                    binding.textProcessing.isVisible = true
                }
                PaymentUiState.Success -> {
                    Toast.makeText(requireContext(), "✅ Успешно оплачено!", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.progressPayment.progress = progress
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SERVICE = "service"
    }
}
