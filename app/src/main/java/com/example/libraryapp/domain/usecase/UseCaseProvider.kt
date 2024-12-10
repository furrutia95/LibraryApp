package com.example.libraryapp.domain.usecase

object UseCaseProvider {
    private var getBooksUseCase: GetBooksUseCase? = null
    private var getBookDetailUseCase: GetBookByIdUseCase? = null
    private var addBookUseCase: AddBookUseCase? = null
    private var deleteBookUseCase: DeleteBookUseCase? = null

    fun provideGetBooksUseCase(): GetBooksUseCase {
        if (getBooksUseCase == null) {
            getBooksUseCase = GetBooksUseCase()
        }
        return getBooksUseCase!!
    }

    // TODO implement the useCases first
  fun provideGetBookDetailUseCase(): GetBookByIdUseCase {
        if (getBookDetailUseCase == null) {
            getBookDetailUseCase = GetBookByIdUseCase()
        }
        return getBookDetailUseCase!!
    }

    fun provideAddBookUseCase(): AddBookUseCase {
        if (addBookUseCase == null) {
            addBookUseCase = AddBookUseCase()
        }
        return addBookUseCase!!
    }

    fun provideDeleteBookUseCase(): DeleteBookUseCase {
        if (deleteBookUseCase == null) {
            deleteBookUseCase = DeleteBookUseCase()
        }
        return deleteBookUseCase!!
    }

}