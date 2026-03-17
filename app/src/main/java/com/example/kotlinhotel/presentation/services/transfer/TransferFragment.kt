package com.example.kotlinhotel.presentation.services.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
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
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

    private lateinit var mapView: MapView

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

        setupRentalMap()

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

    private fun setupRentalMap() {
        mapView = binding.mapRental
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        val center = GeoPoint(55.7558, 37.6173)
        mapView.controller.setZoom(15.5)
        mapView.controller.setCenter(center)

        val mockRentalPoints = listOf(
            Pair(GeoPoint(55.7562, 37.6185), "AutoRent — аренда авто"),
            Pair(GeoPoint(55.7545, 37.6158), "Whoosh — самокаты"),
            Pair(GeoPoint(55.7575, 37.6168), "Яндекс GO — самокаты"),
            Pair(GeoPoint(55.7540, 37.6188), "Hertz — аренда авто"),
            Pair(GeoPoint(55.7570, 37.6152), "Велопрокат")
        )
        mockRentalPoints.forEach { (point, name) ->
            val marker = Marker(mapView)
            marker.position = point
            marker.title = name
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)
        }

        // Prevent parent ScrollView from intercepting touch events on the map,
        // so tap-on-marker (info window) and map panning both work correctly.
        mapView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        if (::mapView.isInitialized) mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (::mapView.isInitialized) mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
