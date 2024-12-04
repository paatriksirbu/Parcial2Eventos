package com.example.parcial2eventos.ejercicio2.ui.listeventos

import com.example.parcial2eventos.ejercicio2.viewmodel.EventoViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parcial2eventos.R
import com.example.parcial2eventos.ejercicio2.ui.registroevento.RegistroEventoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListEventosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddEvento: FloatingActionButton
    private lateinit var eventoViewModel: EventoViewModel
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_eventos)

        // Inicializar vistas con findViewById
        recyclerView = findViewById(R.id.recyclerViewEventos)
        fabAddEvento = findViewById(R.id.fabAddEvento)

        // Configurar RecyclerView
        adapter = EventoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Inicializar ViewModel
        eventoViewModel = ViewModelProvider(this)[EventoViewModel::class.java]

        // Observar eventos en tiempo real
        eventoViewModel.obtenerEventos().observe(this) { eventos ->
            if (eventos.isEmpty()) {
                Toast.makeText(this, getString(R.string.no_hay_eventos), Toast.LENGTH_SHORT).show()
            } else {
                adapter.actualizarEventos(eventos)
            }
        }

        // Botón flotante para agregar un nuevo evento
        fabAddEvento.setOnClickListener {
            startActivity(Intent(this, RegistroEventoActivity::class.java))
        }
    }
}
