package com.example.parcial2eventos.ejercicio1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parcial2eventos.ejercicio1.model.Clase
import javax.inject.Inject

class ClaseViewModel(private val repository: ClaseRepository) : ViewModel() {

    val clasesPorDia: MutableLiveData<List<Clase>> = MutableLiveData()
    val claseActual: MutableLiveData<Clase?> = MutableLiveData()

    fun addClase(clase: Clase, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        repository.addClase(clase, onSuccess, onError)
    }

    fun cargarClasesPorDia(dia: String) : LiveData<List<Clase>> {
        return repository.getClasesPorDia(dia)
    }

    fun cargarClaseActual(dia: String, horaActual: String) {
        repository.getClaseActual(dia, horaActual).observeForever{
            claseActual.value = it
        }
    }
}
