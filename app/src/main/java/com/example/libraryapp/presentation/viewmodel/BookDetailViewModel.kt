package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.UseCaseProvider
import kotlinx.coroutines.launch

class BookDetailViewModel():ViewModel() {

    private val getBookByIdUseCase = UseCaseProvider.provideGetBookDetailUseCase()

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun loadBook(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _book.value = getBookByIdUseCase(id)
            } finally {
                _isLoading.value = false
            }
        }
    }

}