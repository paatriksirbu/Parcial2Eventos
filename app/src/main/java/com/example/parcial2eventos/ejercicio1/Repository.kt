package com.example.parcial2eventos.ejercicio1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parcial2eventos.ejercicio1.model.Clase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class Repository @Inject constructor(private val firestore: FirebaseFirestore){

    fun addClase(clase: Clase): Task<Void> {
        val document = firestore.collection("clases").document()
        val claseWithId = clase.copy(id = document.id)
        return document.set(claseWithId)
    }

    fun getClasesPorDia(dia: String): LiveData<List<Clase>> {
        val clasesLiveData = MutableLiveData<List<Clase>>()
        firestore.collection("clases")
            .whereEqualTo("dia", dia)
            .addSnapshotListener {snapshot, _ ->
                if (snapshot != null) {
                    val clases = snapshot.toObjects(Clase::class.java)
                    clasesLiveData.value = clases
                }
            }
        return clasesLiveData
    }
}