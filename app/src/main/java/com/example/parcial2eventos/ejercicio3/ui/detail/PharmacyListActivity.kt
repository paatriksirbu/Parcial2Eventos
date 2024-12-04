package com.example.parcial2eventos.ejercicio3.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio3.data.repository.PharmacyRepository
import kotlinx.coroutines.launch

class PharmacyListActivity : AppCompatActivity() {
    private val repository = PharmacyRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_list)
        Log.d("PharmacyListActivity", "Actividad creada")

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val pharmacies = repository.getPharmacies()
            recyclerView.adapter = PharmacyAdapter(this@PharmacyListActivity, pharmacies)
        }
    }
}
