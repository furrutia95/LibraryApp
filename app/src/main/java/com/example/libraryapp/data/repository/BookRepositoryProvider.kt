package com.example.libraryapp.data.repository

import com.example.libraryapp.domain.repository.BookRepository

object BookRepositoryProvider {
    private var repository: BookRepository? = null

    // TODO Uncomment this after implement the repository interface on BookRepositoryImpl
    fun provideRepository(): BookRepository {
        if (repository == null) {
            repository = BookRepositoryImpl()
        }
        return repository!!
    }
}