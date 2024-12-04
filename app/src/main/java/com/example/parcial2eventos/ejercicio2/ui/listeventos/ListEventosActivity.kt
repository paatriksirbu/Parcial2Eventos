package com.example.parcial2eventos.ejercicio2.ui.listeventos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio2.data.repository.EventoRepository
import com.example.parcial2eventos.ejercicio2.ui.registroevento.RegistroEventoActivity
import com.example.parcial2eventos.ejercicio2.viewmodel.EventoViewModel
import com.example.parcial2eventos.ejercicio2.viewmodel.EventoViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class ListEventosActivity : AppCompatActivity() {

    private lateinit var eventoViewModel: EventoViewModel
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_eventos)

        // Inicializa el repositorio
        val repository = EventoRepository(FirebaseFirestore.getInstance())
        val factory = EventoViewModelFactory(repository)

        // Inicializa el ViewModel
        eventoViewModel = ViewModelProvider(this, factory)[EventoViewModel::class.java]

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEventos)
        adapter = EventoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observa los cambios en los datos
        eventoViewModel.obtenerEventos().observe(this) { eventos ->
            if (eventos.isNotEmpty()) {
                adapter.actualizarEventos(eventos)
            } else {
                Log.d("ListEventosActivity", "No hay eventos para mostrar")
            }
        }

        val fabAddEvento = findViewById<FloatingActionButton>(R.id.fabAddEvento)
        fabAddEvento.setOnClickListener {
            // Redirigir a RegistroEventoActivity
            val intent = Intent(this, RegistroEventoActivity::class.java)
            startActivity(intent)
        }
    }
}
