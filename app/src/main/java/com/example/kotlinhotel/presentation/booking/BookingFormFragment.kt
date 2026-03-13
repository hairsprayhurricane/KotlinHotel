package com.example.kotlinhotel.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentBookingFormBinding
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class BookingFormFragment : Fragment() {

    private var _binding: FragmentBookingFormBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels(ownerProducer = { requireParentFragment() }) {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookingFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedRoom.observe(viewLifecycleOwner) { room ->
            if (room != null) {
                binding.textSelectedRoom.text = "✅ ${room.type} №${room.number} — ${room.pricePerNight.toInt()} ₽/ночь"
                binding.textSelectedRoom.setTextColor(
                    requireContext().getColor(com.google.android.material.R.color.design_default_color_primary)
                )
            }
        }

        viewModel.selectedDates.observe(viewLifecycleOwner) { dates ->
            if (dates != null) {
                binding.textSelectedDates.text = "📅 ${dates.first} — ${dates.second}"
            }
        }

        binding.btnPickDates.setOnClickListener {
            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Выберите даты")
                .build()
            picker.addOnPositiveButtonClickListener { selection ->
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val start = format.format(Date(selection.first ?: 0))
                val end = format.format(Date(selection.second ?: 0))
                viewModel.setDates(start, end)
            }
            picker.show(parentFragmentManager, "date_picker")
        }

        binding.btnBook.setOnClickListener {
            val name = binding.editName.text.toString()
            val phone = binding.editPhone.text.toString()
            val email = binding.editEmail.text.toString()
            val passport = binding.editPassport.text.toString()

            when {
                viewModel.selectedRoom.value == null ->
                    Toast.makeText(requireContext(), "Выберите номер на вкладке «Номера»", Toast.LENGTH_SHORT).show()
                viewModel.selectedDates.value == null ->
                    Toast.makeText(requireContext(), "Выберите даты заезда и выезда", Toast.LENGTH_SHORT).show()
                name.isBlank() ->
                    Toast.makeText(requireContext(), "Введите ФИО", Toast.LENGTH_SHORT).show()
                phone.isBlank() ->
                    Toast.makeText(requireContext(), "Введите телефон", Toast.LENGTH_SHORT).show()
                email.isBlank() ->
                    Toast.makeText(requireContext(), "Введите email", Toast.LENGTH_SHORT).show()
                else -> viewModel.confirmBooking(name, phone, email, passport)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
