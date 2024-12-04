package com.example.parcial2eventos.ejercicio3.data.repository

import com.example.parcial2eventos.ejercicio3.data.model.Pharmacy
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PharmacyRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("pharmacies")

    suspend fun getPharmacies(): List<Pharmacy> {
        return try {
            val snapshot = collection.get().await()
            snapshot.toObjects(Pharmacy::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
