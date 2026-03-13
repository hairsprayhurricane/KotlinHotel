package com.example.kotlinhotel.presentation.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupQuickActions()
        observeState()
    }

    private fun setupAdapter() {
        recommendationAdapter = RecommendationAdapter { service ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToServices()
            )
        }
        binding.rvRecommendations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }
    }

    private fun setupQuickActions() {
        binding.cardMap.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToHotelInfo())
        }
        binding.cardKey.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToKey())
        }
        binding.cardServices.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToServices())
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (!state.isLoading) {
                        updateBookingCard(state)
                        recommendationAdapter.submitList(state.recommendations)
                    }
                }
            }
        }
    }

    private fun updateBookingCard(state: HomeUiState) {
        val booking = state.booking
        if (booking != null) {
            val fmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            binding.tvRoomNumber.text = "Room ${booking.room.number}"
            binding.tvFloor.text = "Floor ${booking.room.floor}"
            binding.tvCheckIn.text = "Check-in: ${fmt.format(Date(booking.dateStart))}"
            binding.tvCheckOut.text = "Check-out: ${fmt.format(Date(booking.dateEnd))}"
            binding.tvRoomType.text = booking.room.type
            binding.cardBooking.visibility = View.VISIBLE
        } else {
            binding.cardBooking.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
