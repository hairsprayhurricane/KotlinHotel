package com.example.kotlinhotel.presentation.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlinhotel.presentation.services.excursions.ExcursionsFragment
import com.example.kotlinhotel.presentation.services.restaurant.RestaurantFragment
import com.example.kotlinhotel.presentation.services.spa.SpaFragment
import com.example.kotlinhotel.presentation.services.transfer.TransferFragment

class ServicesPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TransferFragment()
        1 -> SpaFragment()
        2 -> ExcursionsFragment()
        3 -> RestaurantFragment()
        else -> TransferFragment()
    }
}
