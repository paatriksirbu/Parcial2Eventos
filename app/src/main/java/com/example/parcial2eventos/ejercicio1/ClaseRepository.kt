package com.example.parcial2eventos.ejercicio1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parcial2eventos.ejercicio1.model.Clase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ClaseRepository @Inject constructor(private val firestore: FirebaseFirestore){

    companion object {
        const val TAG = "Clase Repository"
    }

    fun addClase(clase: Clase, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        try {
            val document = firestore.collection("clases").document()
            val claseWithId = clase.copy(id = document.id)
            document.set(claseWithId)
                .addOnSuccessListener {
                    Log.d(TAG, "Clase añadida: ${clase.nombre}")
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al añadir clase", e)
                    onError(e)
                }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al añadir clase", e)
            onError(e)
        }
    }

    fun getClasesPorDia(dia: String): LiveData<List<Clase>> {
        val clasesLiveData = MutableLiveData<List<Clase>>()

        try {
            firestore.collection("clases")
                .whereEqualTo("dia", dia)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        clasesLiveData.value = emptyList()
                        Log.e(TAG, "Error al obtener clases", e)    // Si hay un error, devuelve una lista vacia.
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val clases = snapshot.toObjects(Clase::class.java)
                        clasesLiveData.value = clases
                        Log.d(TAG, "Clases obtenidas para $dia: ${clases.size}")
                    } else {
                        clasesLiveData.value = emptyList()  // En el caso de que no haya datos.
                        Log.d(TAG, "No hay clases para $dia")
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al obtener clases", e)
        }
        return clasesLiveData
    }

    fun getClaseActual(dia: String, horaActual: String): LiveData<Clase?> {
        val claseLiveData = MutableLiveData<Clase?>()
        try {
            firestore.collection("clases")
                .whereEqualTo("dia", dia)
                .whereLessThanOrEqualTo("horaInicio", horaActual)
                .whereGreaterThanOrEqualTo("horaFin", horaActual)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Error al obtener clase actual", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val clase = snapshot.toObjects(Clase::class.java).firstOrNull()
                        claseLiveData.value = clase
                        Log.d(TAG, "Clase actual obtenida para $dia: ${clase?.nombre}")
                    } else {
                        claseLiveData.value = null
                        Log.d(TAG, "No hay clase en este momento")
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al obtener clase actual", e)
        }
        return claseLiveData
    }
}