package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class AddBookUseCase {
    private val repository = BookRepositoryProvider.provideRepository()
    suspend operator fun invoke(
        title: String,
        author: String,
        year: String,
        description: String,
        isAvailable: Boolean,
        imageUrl: String? = null){

        //Validaciones
        require(title.isNotBlank()) {"Title cannot be blank"}
        require(author.isNotBlank()) {"Author cannot be blank"}
        require(year.toInt() > 0) {"Year must be greater than 0"}

        val book = Book(
            id = 0, // Repository asignará ID
            title = title,
            author = author,
            year = year.toInt(),
            description = description,
            imageUrl = imageUrl,
            isAvailable = isAvailable
        )

        return repository.addBook(book)
    }
}