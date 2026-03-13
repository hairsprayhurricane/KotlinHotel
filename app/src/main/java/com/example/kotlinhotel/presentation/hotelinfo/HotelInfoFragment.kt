package com.example.kotlinhotel.presentation.hotelinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinhotel.R
import com.example.kotlinhotel.databinding.FragmentHotelInfoBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelInfoFragment : Fragment() {

    private var _binding: FragmentHotelInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHotelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HotelInfoPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        val tabTitles = listOf(
            getString(R.string.tab_map),
            getString(R.string.tab_contacts),
            getString(R.string.tab_events)
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
