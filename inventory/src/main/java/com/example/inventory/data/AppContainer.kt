package com.example.inventory.data

import android.content.Context

interface AppContainer {
    val repository: ItemsRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val repository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}