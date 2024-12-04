package com.example.parcial2eventos.ejercicio2.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parcial2eventos.ejercicio2.data.model.Evento
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class EventoRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    fun getEventos(): LiveData<List<Evento>> {
        val eventosLiveData = MutableLiveData<List<Evento>>()
        firestore.collection("eventos")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    eventosLiveData.value = emptyList()
                    return@addSnapshotListener
                }

                val eventos = snapshot?.toObjects(Evento::class.java) ?: emptyList()
                eventosLiveData.value = eventos
            }
        return eventosLiveData
    }

    fun saveEvento(evento: Evento): Task<Void> {
        return firestore.collection("eventos").document(evento.id).set(evento)
    }
}