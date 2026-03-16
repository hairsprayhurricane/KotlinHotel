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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentRoomsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()
    private lateinit var roomAdapter: RoomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFilters()
        observeRooms()
    }

    private fun setupRecyclerView() {
        roomAdapter = RoomAdapter { room ->
            findNavController().navigate(
                RoomsFragmentDirections.actionRoomsToRoomDetail(room.id)
            )
        }
        binding.rvRooms.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = roomAdapter
        }
    }

    private fun setupFilters() {
        val filters = listOf(
            R.string.filter_all,
            R.string.filter_standard,
            R.string.filter_deluxe,
            R.string.filter_suite
        )
        filters.forEach { stringResId ->
            binding.chipGroupFilter.addView(
                com.google.android.material.chip.Chip(requireContext()).apply {
                    text = getString(stringResId)
                    isCheckable = true
                    isChecked = stringResId == R.string.filter_all
                    setOnCheckedChangeListener { _, checked ->
                        if (checked) {
                            viewModel.setFilter(getString(stringResId))
                        } else {
                            // Если пытаются снять выделение, возвращаем текущий активный фильтр
                            val currentFilter = viewModel.selectedFilter.value
                            if (text == currentFilter) {
                                isChecked = true
                            }
                        }
                    }
                }
            )
        }
    }

    private fun observeRooms() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rooms.collect { rooms ->
                    roomAdapter.submitList(rooms)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
