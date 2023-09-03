package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class BooksApiResponse(
    val items: List<Book>
)

@Serializable
data class Book(
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val imageLinks: ImageLinks
)

@Serializable
data class ImageLinks(
    val smallThumbnail : String,
    val thumbnail: String
)