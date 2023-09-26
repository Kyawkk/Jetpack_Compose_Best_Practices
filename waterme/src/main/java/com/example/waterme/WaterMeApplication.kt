package com.example.waterme

import android.app.Application
import com.example.waterme.data.AppContainer
import com.example.waterme.data.AppDefaultContainer

class WaterMeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDefaultContainer(this)
    }
}