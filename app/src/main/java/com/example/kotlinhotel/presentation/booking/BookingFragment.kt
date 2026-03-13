package com.example.kotlinhotel.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentBookingBinding
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    val viewModel: BookingViewModel by viewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val pages = listOf("Номера", "Форма")
        binding.viewPagerBooking.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = pages.size
            override fun createFragment(position: Int) = when (position) {
                0 -> RoomListFragment()
                else -> BookingFormFragment()
            }
        }

        TabLayoutMediator(binding.tabLayoutBooking, binding.viewPagerBooking) { tab, pos ->
            tab.text = pages[pos]
        }.attach()

        viewModel.bookingSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "✅ Номер успешно забронирован!", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
