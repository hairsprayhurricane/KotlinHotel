package com.example.kotlinhotel.presentation.services.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentTransferBinding
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.services.ServicesFragmentDirections
import com.example.kotlinhotel.presentation.services.ServicesViewModel
import com.example.kotlinhotel.presentation.services.excursions.ExcursionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

    private data class BusScheduleItem(val route: String, val departure: String, val arrival: String)

    private val busSchedule = listOf(
        BusScheduleItem("Airport", "08:00", "09:15"),
        BusScheduleItem("City Center", "09:30", "10:00"),
        BusScheduleItem("Beach", "10:30", "10:50"),
        BusScheduleItem("Airport", "14:00", "15:15"),
        BusScheduleItem("City Center", "16:00", "16:30")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateBusSchedule()

        val adapter = ExcursionAdapter { service ->
            findNavController().navigate(
                ServicesFragmentDirections.actionServicesFragmentToPaymentFragment(service)
            )
        }
        binding.rvTransfer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allServices.collect {
                    adapter.submitList(viewModel.getByCategory(ServiceCategory.TRANSFER))
                }
            }
        }
    }

    private fun populateBusSchedule() {
        val rowIds = listOf(R.id.busRow1, R.id.busRow2, R.id.busRow3, R.id.busRow4, R.id.busRow5)
        busSchedule.forEachIndexed { index, item ->
            val row = binding.root.findViewById<View>(rowIds[index])
            row.findViewById<TextView>(R.id.tvRoute).text = item.route
            row.findViewById<TextView>(R.id.tvDeparture).text = item.departure
            row.findViewById<TextView>(R.id.tvArrival).text = item.arrival
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
