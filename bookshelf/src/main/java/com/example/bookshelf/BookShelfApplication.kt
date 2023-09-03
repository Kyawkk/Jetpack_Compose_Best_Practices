package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.di.BookShelfAppContainer
import com.example.bookshelf.di.BookShelfContainer

class BookShelfApplication: Application() {
    lateinit var container: BookShelfContainer
    override fun onCreate() {
        super.onCreate()

        container = BookShelfAppContainer()
    }
}