package com.example.kotlinhotel.presentation.key

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentKeyBinding
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class KeyFragment : Fragment() {

    private var _binding: FragmentKeyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KeyViewModel by viewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.textGuestName.text = user.name
            val booking = user.bookingHistory.firstOrNull()
            binding.textRoomNumber.text = if (booking != null) "№ ${booking.roomNumber}" else "№ —"
        }

        binding.btnTapPhone.setOnClickListener {
            binding.btnTapPhone.isEnabled = false
            viewModel.simulateNfcTap()
        }

        viewModel.doorOpened.observe(viewLifecycleOwner) { opened ->
            if (opened) {
                Snackbar.make(binding.root, "🔓 Дверь открыта! Хорошего отдыха!", Snackbar.LENGTH_LONG).show()
            } else {
                binding.btnTapPhone.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
