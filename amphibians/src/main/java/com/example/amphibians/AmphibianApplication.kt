package com.example.amphibians

import android.app.Application
import com.example.amphibians.data.DefaultContainer

class AmphibianApplication: Application() {
    lateinit var container: DefaultContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}