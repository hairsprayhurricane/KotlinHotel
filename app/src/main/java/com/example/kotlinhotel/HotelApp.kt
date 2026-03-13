package com.example.kotlinhotel

import android.app.Application
import com.example.kotlinhotel.data.repository.HotelRepository
import com.example.kotlinhotel.data.repository.HotelRepositoryImpl
import com.example.kotlinhotel.data.source.MockDataSource

class HotelApp : Application() {
    val repository: HotelRepository by lazy {
        HotelRepositoryImpl(MockDataSource(this))
    }
}
