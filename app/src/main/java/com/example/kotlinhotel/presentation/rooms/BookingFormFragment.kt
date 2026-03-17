package com.example.kotlinhotel.presentation.rooms

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentBookingFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookingFormViewModel by viewModels()

    private var checkInMillis: Long = 0L
    private var checkOutMillis: Long = 0L
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookingFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val now = Calendar.getInstance()
        checkInMillis = now.timeInMillis
        binding.btnCheckInDate.text = dateFormat.format(now.time)
        now.add(Calendar.DAY_OF_MONTH, 3)
        checkOutMillis = now.timeInMillis
        binding.btnCheckOutDate.text = dateFormat.format(now.time)

        binding.btnCheckInDate.setOnClickListener { showDatePicker(true) }
        binding.btnCheckOutDate.setOnClickListener { showDatePicker(false) }

        binding.btnConfirmBooking.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val passport = binding.etPassport.text.toString().trim()
            if (name.isBlank() || email.isBlank() || phone.isBlank()) {
                Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (checkInMillis >= checkOutMillis) {
                Snackbar.make(binding.root, "Check-out must be after check-in", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.submitBooking(name, email, phone, passport, checkInMillis, checkOutMillis)
        }
        observeState()
    }

    private fun showDatePicker(isCheckIn: Boolean) {
        val cal = Calendar.getInstance().apply {
            timeInMillis = if (isCheckIn) checkInMillis else checkOutMillis
        }
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val selected = Calendar.getInstance().apply { set(year, month, day) }
                if (isCheckIn) {
                    checkInMillis = selected.timeInMillis
                    binding.btnCheckInDate.text = dateFormat.format(selected.time)
                } else {
                    checkOutMillis = selected.timeInMillis
                    binding.btnCheckOutDate.text = dateFormat.format(selected.time)
                }
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
        }.show()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is BookingFormState.Loading -> binding.btnConfirmBooking.isEnabled = false
                        is BookingFormState.Success -> {
                            findNavController().navigate(
                                R.id.homeFragment,
                                null,
                                NavOptions.Builder()
                                    .setPopUpTo(R.id.nav_graph, false)
                                    .setLaunchSingleTop(true)
                                    .build()
                            )
                        }
                        is BookingFormState.Error -> {
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_SHORT).show()
                            binding.btnConfirmBooking.isEnabled = true
                        }
                        else -> binding.btnConfirmBooking.isEnabled = true
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
