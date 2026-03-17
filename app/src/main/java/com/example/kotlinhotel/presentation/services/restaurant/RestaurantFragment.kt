package com.example.kotlinhotel.presentation.services.restaurant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.databinding.FragmentRestaurantBinding
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.services.ServicesFragmentDirections
import com.example.kotlinhotel.presentation.services.ServicesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

    private lateinit var mapView: MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MenuAdapter { service ->
            findNavController().navigate(
                ServicesFragmentDirections.actionServicesFragmentToPaymentFragment(service)
            )
        }
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        setupRestaurantMap()

        binding.btnOrderToRoom.setOnClickListener {
            val foodServices = viewModel.getByCategory(ServiceCategory.FOOD)
            if (foodServices.isNotEmpty()) {
                val orderService = HotelService(
                    id = "room_order",
                    title = "Room Service Order",
                    description = "Food delivery to your room",
                    price = foodServices.sumOf { it.price } / foodServices.size,
                    category = ServiceCategory.FOOD
                )
                findNavController().navigate(
                    ServicesFragmentDirections.actionServicesFragmentToPaymentFragment(orderService)
                )
            } else {
                Snackbar.make(binding.root, "No items available", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnDelivery.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/eda"))
            startActivity(intent)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allServices.collect {
                    adapter.submitList(viewModel.getByCategory(ServiceCategory.FOOD))
                }
            }
        }
    }

    private fun setupRestaurantMap() {
        mapView = binding.mapNearbyRestaurants
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        val center = GeoPoint(55.7558, 37.6173)
        mapView.controller.setZoom(15.5)
        mapView.controller.setCenter(center)

        val mockRestaurants = listOf(
            Pair(GeoPoint(55.7570, 37.6185), "Ресторан «Высота»"),
            Pair(GeoPoint(55.7545, 37.6160), "Bistro Central"),
            Pair(GeoPoint(55.7578, 37.6150), "Sushi Neko"),
            Pair(GeoPoint(55.7538, 37.6195), "Pizza Roma"),
            Pair(GeoPoint(55.7565, 37.6140), "Кафе «Уют»")
        )
        mockRestaurants.forEach { (point, name) ->
            val marker = Marker(mapView)
            marker.position = point
            marker.title = name
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)
        }

        // Prevent parent NestedScrollView from intercepting touch events on the map,
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
