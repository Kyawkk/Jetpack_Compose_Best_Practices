package com.example.marsphoto.data

import kotlinx.serialization.Serializable

@Serializable
data class MarsPhoto(
    val id: String,
    val img_src: String,
)
