package com.example.marsphoto.fake

import com.example.marsphoto.network.MarsUiState
import com.example.marsphoto.ui.screens.MarsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MarsViewModelTest {
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() = runTest {
        val marsViewModel = MarsViewModel(
            marsPhotosRepository = FakeNetworkMarsPhotosRepository()
        )
    }
}