package com.example.kotlinhotel.presentation.services.restaurant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
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
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.services.ServicesFragmentDirections
import com.example.kotlinhotel.presentation.services.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantFragment : Fragment() {

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
