package com.example.marsphoto.fake

import com.example.marsphoto.data.MarsPhoto
import com.example.marsphoto.network.MarsApiService

class FakeMarsApiService: MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}