package com.example.bluromatic.data

import android.content.Context

interface AppContainer {
    val repository: BluromaticRepository
}

class DefaultAppContainer (context: Context): AppContainer{
    override val repository: BluromaticRepository = WorkManagerBluromaticRepository(context)
}