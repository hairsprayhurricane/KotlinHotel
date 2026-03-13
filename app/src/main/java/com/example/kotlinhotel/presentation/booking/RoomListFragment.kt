package com.example.kotlinhotel.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentRoomListBinding
import com.example.kotlinhotel.presentation.ViewModelFactory

class RoomListFragment : Fragment() {

    private var _binding: FragmentRoomListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels(ownerProducer = { requireParentFragment() }) {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var adapter: RoomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RoomAdapter { room ->
            viewModel.selectRoom(room)
            Toast.makeText(requireContext(), "Выбран: ${room.type} №${room.number}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerRooms.apply {
            this.adapter = this@RoomListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            adapter.submitList(rooms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
