package com.example.kotlinhotel.presentation.hotelinfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HotelInfoPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HotelMapFragment()
        1 -> HotelContactsFragment()
        2 -> HotelEventsFragment()
        else -> HotelMapFragment()
    }
}
