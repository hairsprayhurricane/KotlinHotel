package com.example.kotlinhotel.presentation.services.excursions

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
import com.example.kotlinhotel.databinding.FragmentExcursionsBinding
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.services.ServicesFragmentDirections
import com.example.kotlinhotel.presentation.services.ServicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExcursionsFragment : Fragment() {

    private var _binding: FragmentExcursionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentExcursionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ExcursionAdapter { service ->
            findNavController().navigate(
                ServicesFragmentDirections.actionServicesFragmentToPaymentFragment(service)
            )
        }
        binding.rvExcursions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allServices.collect {
                    adapter.submitList(viewModel.getByCategory(ServiceCategory.EXCURSION))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
