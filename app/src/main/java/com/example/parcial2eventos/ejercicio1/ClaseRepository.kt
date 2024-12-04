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

    fun addClase(clase: Clase, onSuccess: () -> Unit, onError: (Exception) -> Unit): Task<Void> {
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
                        Log.e(TAG, "Error al obtener clases", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val clases = snapshot.toObjects(Clase::class.java)
                        clasesLiveData.value = clases
                        Log.d(TAG, "Clases obtenidas para $dia: ${clases.size}")
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción al obtener clases", e)
        }
        return clasesLiveData
    }

    fun getClaseActual(dia: String, horaActual: String): LiveData<Clase?> {
        val claseLiveData = MutableLiveData<Clase?>()
        firestore.collection("clases")
            .whereEqualTo("dia", dia)
            .whereLessThanOrEqualTo("horaInicio", horaActual)
            .whereGreaterThanOrEqualTo("horaFin", horaActual)
            .addSnapshotListener { snapshot, _ ->
                claseLiveData.value = snapshot?.toObjects(Clase::class.java)?.firstOrNull()
            }
        return claseLiveData
    }
}