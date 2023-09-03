package com.example.bookshelf.di

import com.example.bookshelf.network.BookApiService
import com.example.bookshelf.network.BookShelfRepository
import com.example.bookshelf.network.BookShelfRepositoryImpl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface BookShelfContainer {
    val repository: BookShelfRepository
}

class BookShelfAppContainer: BookShelfContainer{
    val baseUrl = "https://www.googleapis.com/books/v1/"

    val gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .build()

    val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }

    override val repository: BookShelfRepository by lazy {
        BookShelfRepositoryImpl(retrofitService)
    }
}