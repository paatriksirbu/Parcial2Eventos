package com.example.parcial2eventos.ejercicio2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parcial2eventos.ejercicio2.data.repository.EventoRepository

class EventoViewModelFactory(private val repository: EventoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventoViewModel::class.java)) {
            return EventoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
