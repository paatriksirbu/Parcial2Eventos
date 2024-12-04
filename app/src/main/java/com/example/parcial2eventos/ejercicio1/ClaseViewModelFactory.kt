package com.example.parcial2eventos.ejercicio1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClaseViewModelFactory(private val repository: ClaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}