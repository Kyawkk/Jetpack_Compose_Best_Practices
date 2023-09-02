package com.example.marsphoto.fake

import com.example.marsphoto.data.MarsPhoto
import com.example.marsphoto.data.MarsPhotosRepository

class FakeNetworkMarsPhotosRepository: MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}