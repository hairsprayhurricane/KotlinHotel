package com.example.kotlinhotel.presentation.services.spa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinhotel.databinding.FragmentSpaBinding
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.services.ServicesViewModel
import com.example.kotlinhotel.presentation.services.excursions.ExcursionAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpaFragment : Fragment() {

    private var _binding: FragmentSpaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSpaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ExcursionAdapter { service ->
            Snackbar.make(binding.root, "SPA session booked!", Snackbar.LENGTH_SHORT).show()
        }
        binding.rvSpa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        binding.btnBookSpa.setOnClickListener {
            Snackbar.make(binding.root, "SPA session booked!", Snackbar.LENGTH_SHORT).show()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allServices.collect {
                    adapter.submitList(viewModel.getByCategory(ServiceCategory.SPA))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
