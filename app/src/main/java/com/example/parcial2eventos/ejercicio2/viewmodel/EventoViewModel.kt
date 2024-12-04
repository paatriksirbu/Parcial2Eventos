package com.example.parcial2eventos.ejercicio2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.parcial2eventos.ejercicio2.data.model.Evento
import com.example.parcial2eventos.ejercicio2.data.repository.EventoRepository
import com.google.android.gms.tasks.Task

class EventoViewModel(private val repository: EventoRepository) : ViewModel() {

    fun obtenerEventos(): LiveData<List<Evento>> {
        return repository.getEventos()
    }

    fun guardarEvento(evento: Evento): Task<Void> {
        return repository.saveEvento(evento)
    }
}
