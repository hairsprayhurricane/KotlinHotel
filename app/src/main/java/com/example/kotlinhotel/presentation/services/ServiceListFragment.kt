package com.example.kotlinhotel.presentation.services

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
import com.example.kotlinhotel.databinding.FragmentServiceListBinding
import com.example.kotlinhotel.domain.model.HotelService
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.example.kotlinhotel.presentation.payment.PaymentFragment

class ServiceListFragment : Fragment() {

    private var _binding: FragmentServiceListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ServicesViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private lateinit var adapter: ServiceAdapter
    private lateinit var category: ServiceCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getSerializable(ARG_CATEGORY) as? ServiceCategory ?: ServiceCategory.SPA
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentServiceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ServiceAdapter { service -> navigateToPayment(service) }
        binding.recyclerServices.apply {
            this.adapter = this@ServiceListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.allServices.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.getServicesByCategory(category))
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

    companion object {
        const val ARG_CATEGORY = "category"

        fun newInstance(category: ServiceCategory) = ServiceListFragment().apply {
            arguments = Bundle().apply { putSerializable(ARG_CATEGORY, category) }
        }
    }
}
