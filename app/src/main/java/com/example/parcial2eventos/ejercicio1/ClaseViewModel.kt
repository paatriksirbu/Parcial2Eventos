package com.example.parcial2eventos.ejercicio1

import androidx.lifecycle.MutableLiveData
import com.example.parcial2eventos.ejercicio1.model.Clase
import javax.inject.Inject


class ClaseViewModel @Inject constructor(private val repository: ClaseRepository) : ViewModel() {

    val clasesPorDia = MutableLiveData<List<Clase>>()
    val claseActual = MutableLiveData<Clase?>()

    fun addClase(clase: Clase) {
        repository.addClase(clase).addOnCompleteListener {
            if (it.isSuccessful) {
                // Handle success
            }
        }
    }

    fun cargarClasesPorDia(dia: String) {
        clasesPorDia.value = repository.getClasesPorDia(dia).value
    }

    fun cargarClaseActual(dia: String, horaActual: String) {
        claseActual.value = repository.getClaseActual(dia, horaActual).value
    }
}
