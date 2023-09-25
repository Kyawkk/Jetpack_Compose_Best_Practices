package com.example.bluromatic

import android.app.Application
import com.example.bluromatic.data.BluromaticRepository
import com.example.bluromatic.data.WorkManagerBluromaticRepository

class BluromaticApplication : Application() {
    lateinit var repository: BluromaticRepository

    override fun onCreate() {
        super.onCreate()
        repository = WorkManagerBluromaticRepository(this)
    }
}