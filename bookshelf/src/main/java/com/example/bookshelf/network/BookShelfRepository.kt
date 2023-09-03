package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BooksApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookShelfRepository {
    suspend fun getBooks(): BooksApiResponse
}

class BookShelfRepositoryImpl (private val apiService: BookApiService): BookShelfRepository{
    override suspend fun getBooks(): BooksApiResponse {
        return apiService.getBooks()
    }
}