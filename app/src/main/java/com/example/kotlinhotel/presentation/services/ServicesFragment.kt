package com.example.kotlinhotel.presentation.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlinhotel.HotelApp
import com.example.kotlinhotel.databinding.FragmentServicesBinding
import com.example.kotlinhotel.domain.model.ServiceCategory
import com.example.kotlinhotel.presentation.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    val viewModel: ServicesViewModel by activityViewModels {
        ViewModelFactory((requireActivity().application as HotelApp).repository)
    }

    private val tabTitles = listOf("SPA", "Ресторан", "Трансфер", "Экскурсии", "Уборка", "Прачечная")
    private val tabCategories = listOf(
        ServiceCategory.SPA,
        ServiceCategory.FOOD,
        ServiceCategory.TRANSFER,
        ServiceCategory.EXCURSION,
        ServiceCategory.CLEANING,
        ServiceCategory.LAUNDRY
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabCategories.size
            override fun createFragment(position: Int): Fragment {
                return if (tabCategories[position] == ServiceCategory.FOOD) {
                    RestaurantFragment()
                } else {
                    ServiceListFragment.newInstance(tabCategories[position])
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
