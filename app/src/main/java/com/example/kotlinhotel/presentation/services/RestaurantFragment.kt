package com.example.kotlinhotel.presentation.services

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentRestaurantBinding
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.example.kotlinhotel.presentation.payment.PaymentFragment

class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ServicesViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var adapter: ServiceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ServiceAdapter { service -> navigateToPayment(service) }
        binding.recyclerMenu.apply {
            this.adapter = this@RestaurantFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.allServices.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.getServicesByCategory(ServiceCategory.FOOD))
        }

        binding.btnOrderToRoom.setOnClickListener {
            val foodServices = viewModel.getServicesByCategory(ServiceCategory.FOOD)
            val first = foodServices.firstOrNull()
            if (first != null) navigateToPayment(first)
        }

        binding.btnDelivery.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://eda.yandex.ru"))
            startActivity(intent)
        }
    }

    private fun navigateToPayment(service: HotelService) {
        val bundle = Bundle().apply {
            putParcelable(PaymentFragment.ARG_SERVICE, service)
        }
        findNavController().navigate(R.id.action_services_to_payment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
