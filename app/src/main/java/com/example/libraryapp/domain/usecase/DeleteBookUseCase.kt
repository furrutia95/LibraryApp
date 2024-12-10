package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider

class DeleteBookUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    suspend operator fun invoke(id: Int) {
        return repository.deleteBook(id)
    }
}