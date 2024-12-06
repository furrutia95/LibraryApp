package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class GetBooksUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    suspend operator fun invoke(): List<Book> {
        return repository.getBooks()
    }
}