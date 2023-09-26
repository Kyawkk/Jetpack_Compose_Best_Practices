package com.example.waterme.data

import android.content.Context

interface AppContainer {
    val waterRepository: WaterRepository
}

class AppDefaultContainer(context: Context) : AppContainer{
    override val waterRepository: WaterRepository = WorkManagerRepository(context)
}