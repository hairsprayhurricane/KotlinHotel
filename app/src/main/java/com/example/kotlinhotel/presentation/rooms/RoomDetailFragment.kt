package com.example.kotlinhotel.presentation.rooms

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
import com.example.kotlinhotel.databinding.FragmentRoomDetailBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class RoomDetailFragment : Fragment() {

    private var _binding: FragmentRoomDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomDetailViewModel by viewModels()
    private val args: RoomDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRoom()
    }

    private fun observeRoom() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.room.collect { room ->
                    room ?: return@collect
                    val fmt = NumberFormat.getNumberInstance(Locale("ru"))
                    binding.tvRoomType.text = room.type
                    binding.tvRoomNumber.text = "Room ${room.number}"
                    binding.tvRoomFloor.text = "Floor ${room.floor}"
                    binding.tvRoomPrice.text = "${fmt.format(room.pricePerNight.toInt())} ₽ / night"
                    binding.tvRoomDescription.text = room.description
                    binding.chipGroupAmenities.removeAllViews()
                    room.amenities.forEach { amenity ->
                        binding.chipGroupAmenities.addView(
                            Chip(requireContext()).apply { text = amenity }
                        )
                    }
                    binding.btnContinue.setOnClickListener {
                        findNavController().navigate(
                            RoomDetailFragmentDirections.actionRoomDetailToBookingForm(room.id)
                        )
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
