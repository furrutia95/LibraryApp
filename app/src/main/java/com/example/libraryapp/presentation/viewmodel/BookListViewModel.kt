package com.example.libraryapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.UseCaseProvider
import kotlinx.coroutines.launch

class BookListViewModel : ViewModel() {

    private val getBooksUseCase = UseCaseProvider.provideGetBooksUseCase()
    private val addBookUseCase = UseCaseProvider.provideAddBookUseCase()
    private val deleteBookUseCase = UseCaseProvider.provideDeleteBookUseCase()

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _books.value = getBooksUseCase()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addBook(title: String, author: String, year: String, description: String, isAvailable: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addBookUseCase(title, author, year, description, isAvailable)
                loadBooks() // Recargar lista después de agregar
                Log.d("BookListViewModel", "Book added successfully")
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                deleteBookUseCase(id)
                loadBooks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}