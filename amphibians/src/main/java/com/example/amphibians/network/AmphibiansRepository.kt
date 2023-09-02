package com.example.amphibians.network

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibiansRepository (private val amphibianApiService: AmphibianApiService): AmphibiansRepository{
    override suspend fun getAmphibians(): List<Amphibian> {
        return amphibianApiService.getAmphibians()
    }
}