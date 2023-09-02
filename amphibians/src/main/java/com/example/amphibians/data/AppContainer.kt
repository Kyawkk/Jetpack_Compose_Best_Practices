package com.example.amphibians.data

import com.example.amphibians.network.AmphibianApiService
import com.example.amphibians.network.AmphibiansRepository
import com.example.amphibians.network.NetworkAmphibiansRepository
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val repository: AmphibiansRepository
}

class DefaultContainer: AppContainer{
    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com/"

    val gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AmphibianApiService by lazy {
        retrofit.create(AmphibianApiService::class.java)
    }

    override val repository: AmphibiansRepository by lazy {
        NetworkAmphibiansRepository(retrofitService)
    }
}