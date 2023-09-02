package com.example.marsphoto.fake

import com.example.marsphoto.data.NetworkMarsPhotosRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkMarsRepositoryTest {
    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() = runTest {
        val repository = NetworkMarsPhotosRepository(FakeMarsApiService())
        assertEquals(FakeDataSource.photosList,repository.getMarsPhotos())
    }
}