package com.example.kotlinhotel.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentHomeBinding
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.example.kotlinhotel.presentation.payment.PaymentFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecycler() {
        recommendationAdapter = RecommendationAdapter { service ->
            navigateToPayment(service)
        }
        binding.recyclerRecommendations.apply {
            adapter = recommendationAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupClickListeners() {
        binding.btnHotelMap.setOnClickListener {
            findNavController().navigate(R.id.hotelInfoFragment)
        }
        binding.btnKey.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_key)
        }
        binding.btnServices.setOnClickListener {
            findNavController().navigate(R.id.servicesFragment)
        }
        binding.btnBookRoom.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_booking)
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.textGreeting.text = "Добро пожаловать!"
            binding.textUserName.text = user.name

            val booking = user.bookingHistory.firstOrNull()
            if (booking != null) {
                binding.cardBooking.visibility = View.VISIBLE
                binding.textRoomNumber.text = "№${booking.roomNumber} (${booking.roomType}, ${booking.floor} этаж)"
                binding.textDates.text = "${booking.dateStart} — ${booking.dateEnd}"
                binding.textCleaning.text = "Сегодня в ${booking.cleaningTime}"
            }
        }

        viewModel.recommendations.observe(viewLifecycleOwner) { services ->
            recommendationAdapter.submitList(services)
        }
    }

    private fun navigateToPayment(service: HotelService) {
        val bundle = Bundle().apply {
            putParcelable(PaymentFragment.ARG_SERVICE, service)
        }
        findNavController().navigate(R.id.action_home_to_payment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
