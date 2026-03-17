package com.example.kotlinhotel

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class HotelApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(this, getSharedPreferences(packageName, Context.MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = packageName
    }
}
