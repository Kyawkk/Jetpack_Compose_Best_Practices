package com.example.bookshelf.network

import com.example.bookshelf.model.BooksApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String = "Android"
    ): BooksApiResponse
}